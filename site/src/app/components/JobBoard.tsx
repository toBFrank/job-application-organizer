'use client';
import React, { useState, useEffect } from 'react';
import confetti from 'canvas-confetti';

type Application = {
  id: number;
  title: string;
  company: string;
  status: string;
  interviews?: any[];
};

type ColumnId = 'applied' | 'interviewing' | 'offer' | 'rejected';
type Columns = Record<
  ColumnId,
  { id: string; col_title: string; apps: Application[] }
>;

const DEFAULT_COLUMNS: Columns = {
  applied: { id: 'applied', col_title: 'Applied', apps: [] },
  interviewing: { id: 'interviewing', col_title: 'Interviewing', apps: [] },
  offer: { id: 'offer', col_title: 'Offer', apps: [] },
  rejected: { id: 'rejected', col_title: 'Rejected', apps: [] },
};

interface JobBoardProps {
  refreshTrigger?: number;
}

export default function Job_Board({ refreshTrigger = 0 }: JobBoardProps) {
  const [columns, setColumns] = useState<Columns>(DEFAULT_COLUMNS);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [deletingIds, setDeletingIds] = useState<Set<number>>(new Set());
  const [showCelebration, setShowCelebration] = useState(false);

  // Store the dragged app + source column
  const [dragged, setDragged] = useState<{ appId: string; from: ColumnId } | null>(null);

  // Map backend status to column IDs
  const statusToColumnMap: Record<string, ColumnId> = {
    'APPLIED': 'applied',
    'INTERVIEWING': 'interviewing', 
    'OFFER': 'offer',
    'REJECTED': 'rejected'
  };

  // Fetch applications from backend
  useEffect(() => {
    const fetchApplications = async () => {
      try {
        setLoading(true);
        setError(null);
        
        const response = await fetch('http://localhost:8080/applications');
        
        if (!response.ok) {
          throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const applications: Application[] = await response.json();
        
        // Debug: Log applications to check for duplicates
        console.log('Fetched applications:', applications);
        console.log('Application IDs:', applications.map(app => app.id));
        
        // Group applications by status
        const newColumns = { ...DEFAULT_COLUMNS };
        
        // Clear all columns first
        Object.keys(newColumns).forEach(key => {
          newColumns[key as ColumnId].apps = [];
        });
        
        applications.forEach(app => {
          const columnId = statusToColumnMap[app.status];
          if (columnId) {
            // Check if application already exists in this column to prevent duplicates
            const exists = newColumns[columnId].apps.some(existingApp => existingApp.id === app.id);
            if (!exists) {
              newColumns[columnId].apps.push(app);
            }
          }
        });
        
        setColumns(newColumns);
      } catch (err) {
        console.error('Error fetching applications:', err);
        setError(err instanceof Error ? err.message : 'Failed to fetch applications');
      } finally {
        setLoading(false);
      }
    };

    fetchApplications();
  }, [refreshTrigger]);

  // Update application status in backend
  const updateApplicationStatus = async (appId: number, newStatus: string, currentApp: Application) => {
    try {
      const response = await fetch(`http://localhost:8080/applications/${appId}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          id: appId,
          title: currentApp.title,
          company: currentApp.company,
          status: newStatus
        }),
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
    } catch (err) {
      console.error('Error updating application status:', err);
      setError('Failed to update application status');
    }
  };

  // Delete application from backend
  const deleteApplication = async (appId: number) => {
    try {
      setDeletingIds(prev => new Set(prev).add(appId));
      
      const response = await fetch(`http://localhost:8080/applications/${appId}`, {
        method: 'DELETE',
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      // Remove from local state
      setColumns((prev) => {
        const newColumns = { ...prev };
        Object.keys(newColumns).forEach(key => {
          newColumns[key as ColumnId].apps = newColumns[key as ColumnId].apps.filter(app => app.id !== appId);
        });
        return newColumns;
      });

    } catch (err) {
      console.error('Error deleting application:', err);
      setError('Failed to delete application');
    } finally {
      setDeletingIds(prev => {
        const newSet = new Set(prev);
        newSet.delete(appId);
        return newSet;
      });
    }
  };

  // Handle delete confirmation
  const handleDeleteApplication = (appId: number, appTitle: string) => {
    if (window.confirm(`Are you sure you want to delete the application for "${appTitle}"?`)) {
      deleteApplication(appId);
    }
  };

  // Trigger confetti animation for offer column
  const triggerConfetti = () => {
    // Show celebration message
    setShowCelebration(true);
    setTimeout(() => setShowCelebration(false), 3000);

    // Create a burst of confetti from the center
    confetti({
      particleCount: 100,
      spread: 70,
      origin: { y: 0.6 },
      colors: ['#10b981', '#059669', '#047857', '#065f46', '#064e3b'], // Teal colors to match the theme
    });

    // Add a second burst after a short delay
    setTimeout(() => {
      confetti({
        particleCount: 50,
        spread: 50,
        origin: { y: 0.4 },
        colors: ['#f59e0b', '#d97706', '#b45309', '#92400e', '#78350f'], // Amber colors
      });
    }, 200);
  };

  function onDragStart(
    e: React.DragEvent<HTMLDivElement>,
    appId: number,
    from: ColumnId
  ) {
    setDragged({ appId: appId.toString(), from });
    e.dataTransfer.effectAllowed = 'move';
  }

  function onDragOver(e: React.DragEvent<HTMLDivElement>) {
    e.preventDefault(); // needed so drop works
  }

  function onDrop(e: React.DragEvent<HTMLElement>, to: ColumnId) {
    e.preventDefault();
    if (!dragged) return;

    const { appId, from } = dragged;
    if (from === to) return;

    const appIdNum = parseInt(appId);
    const newStatus = Object.keys(statusToColumnMap).find(
      key => statusToColumnMap[key] === to
    );

    if (!newStatus) return;

    // Update local state first for immediate UI feedback
    setColumns((prev) => {
      const app = prev[from].apps.find((a) => a.id === appIdNum);
      if (!app) return prev;

      const updatedApp = { ...app, status: newStatus };

      // Update backend asynchronously
      updateApplicationStatus(appIdNum, newStatus, app);

      // Trigger confetti if moving to offer column
      if (to === 'offer') {
        triggerConfetti();
      }

      return {
        ...prev,
        [from]: {
          ...prev[from],
          apps: prev[from].apps.filter((a) => a.id !== appIdNum),
        },
        [to]: {
          ...prev[to],
          apps: [...prev[to].apps, updatedApp],
        },
      };
    });

    setDragged(null);
  }

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-[300px]">
        <div className="text-lg text-slate-600">Loading applications...</div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="flex items-center justify-center min-h-[300px]">
        <div className="text-lg text-red-600">
          Error: {error}
          <button 
            onClick={() => window.location.reload()} 
            className="ml-4 px-4 py-2 bg-blue-500 text-white rounded hover:bg-blue-600"
          >
            Retry
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="relative">
      {/* Celebration Message */}
      {showCelebration && (
        <div className="fixed top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 z-50 bg-gradient-to-r from-green-500 to-teal-500 text-white px-8 py-4 rounded-lg shadow-2xl text-2xl font-bold animate-bounce">
          🎉 Congratulations! You got an offer! 🎉
        </div>
      )}
      
      <div className="grid grid-cols-4">
        {(Object.keys(columns) as ColumnId[]).map((colId) => {
        const col = columns[colId];
        return (
          <section
            key={col.id}
            onDragOver={onDragOver}
            onDrop={(e) => onDrop(e, col.id as ColumnId)}
            className="bg-white border-r border-t border-gray-100 shadow-sm flex flex-col"
          >
            {/* Column Headers */}
            <div className="bg-gray-50">
              <h2 className="p-4 text-lg font-medium border-b border-gray-100 mb-3 flex items-center justify-between">
                <span>{col.col_title}</span>
                <span className="text-sm text-slate-500">
                  ({col.apps.length})
                </span>
              </h2>
            </div>

            <div className="flex-1 space-y-3 p-4">
              {col.apps.length === 0 && (
                <div className="text-sm text-slate-400">No applications</div>
              )}

              {col.apps.map((app, index) => (
                <article
                  key={`${col.id}-${app.id}-${index}`}
                  draggable
                  onDragStart={(e) => onDragStart(e as React.DragEvent<HTMLDivElement>, app.id, col.id as ColumnId)}
                  className="bg-slate-50 p-3 rounded-md border cursor-grab hover:shadow-sm"
                >
                  <div className="flex justify-between items-start gap-2">
                    <div>
                      <h3 className="font-semibold text-sm">{app.title}</h3>
                      {app.company ? (
                        <p className="text-xs text-slate-600 mt-1">{app.company}</p>
                      ) : null}
                    </div>
                    <div className="flex items-center gap-1">
                      <button
                        onClick={(e) => {
                          e.stopPropagation(); // Prevent drag when clicking delete
                          handleDeleteApplication(app.id, app.title);
                        }}
                        disabled={deletingIds.has(app.id)}
                        aria-label={`delete-${app.id}`}
                        className="text-xs px-2 py-1 border rounded hover:bg-red-50 hover:border-red-300 hover:text-red-600 transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
                      >
                        {deletingIds.has(app.id) ? 'Deleting...' : 'Delete'}
                      </button>
                    </div>
                  </div>
                </article>
              ))}
            </div>

            <div className="mt-4 p-4">
              <small className="text-xs text-slate-400">
                Drag applications between columns.
              </small>
            </div>
          </section>
        );
      })}
      </div>
    </div>
  );
}