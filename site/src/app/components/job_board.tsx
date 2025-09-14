'use client';
import React, { useEffect, useState } from 'react';

// test Application type and applications
type Application = {
    id: string;
    title: string;
    company: string;
    rejected: boolean;
    jobOffer: boolean;
}
let test1: Application = 
  {
      id: '1',
      title: 'Java Developer', 
      company: 'Microsoft', 
      rejected: false, 
      jobOffer: false
  };
let test2: Application = 
  {
      id: '2',
      title: 'Software Engineer',
      company: 'Roblox',
      rejected: false,
      jobOffer: false
  }

type ColumnId = 'applied' | 'interviewing' | 'offer' | 'rejected';
type Columns = Record<ColumnId, {id: string; col_title: string; apps: Application[]}>;
const DEFAULT_COLUMNS: Columns = {
  applied: { id: "applied", col_title: "Applied", apps: [test1] },
  interviewing: { id: "interviewing", col_title: "Interviewing", apps: [test2] },
  offer: { id: "offer", col_title: "Offer", apps: [] },
  rejected: { id: 'rejected', col_title: 'Rejected', apps: []}
};

export default function Job_Board() {
    const [columns, setColumns] = useState<Columns>(DEFAULT_COLUMNS);

    return (
        <div className="grid grid-cols-4">
            {(Object.keys(columns) as ColumnId[]).map((colId) => {
              const col = columns[colId];
              return (
                // Columns 
                <section
                  key={col.id}
                  //onDragOver={onDragOver}
                  //onDrop={(e) => onDropOnColumn(e, col.id)}
                  className="bg-white border-r border-t border-gray-100 shadow-sm flex flex-col"
                >
                  {/* Column Headers */}
                  <div className='bg-gray-50'>
                      <h2 className="p-4 text-lg semi-bold font-medium border-b border-gray-100 mb-3 flex items-center justify-between">
                      <span>{col.col_title}</span>
                      <span className="text-sm text-slate-500">({col.apps.length})</span>
                      </h2>
                  </div>
                  
                  <div className="flex-1 space-y-3 min-h-[120px] p-4">
                    {/* Column Data */}
                    {col.apps.length === 0 && <div className="text-sm text-slate-400">No applications</div>}

                    {col.apps.map((app) => (
                      <article
                        key={app.id}
                        draggable
                        //onDragStart={(e) => onDragStart(e, app.id, col.id)}
                        className="bg-slate-50 p-3 rounded-md border cursor-grab hover:shadow-sm"
                      >
                        {/* Application Tiles */}
                        <div className="flex justify-between items-start gap-2">
                          <div>
                            <h3 className="font-semibold text-sm">{app.title}</h3>
                            {app.company ? <p className="text-xs text-slate-600 mt-1">{app.company}</p> : null}
                          </div>
                          <div className="flex items-center gap-1">
                            <button
                              aria-label={`delete-${app.id}`}
                              className="text-xs px-2 py-1 border rounded hover:bg-white"
                              //onClick={() => deleteCard(col.id, card.id)}
                            >
                              Delete
                            </button>
                          </div>
                        </div>
                      </article>
                        ))}
                    </div>
                    {/* Extra Info */}
                    <div className="mt-4 p-4">
                        <small className="text-xs text-slate-400">Drag applications between columns.</small>
                    </div>
                </section>
              )})}
          </div>
    )
}