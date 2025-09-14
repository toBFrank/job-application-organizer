// app/page.tsx (Next.js 13+ / app directory)


"use client";


import React, { useState } from "react";
import { DragDropContext, Droppable, Draggable, DropResult } from "@hello-pangea/dnd";


interface Task {
 id: string;
 content: string;
}


interface Column {
 id: string;
 title: string;
 tasks: Task[];
}


const initialData: Record<string, Column> = {
 "column-1": {
   id: "column-1",
   title: "To Do",
   tasks: [
     { id: "task-1", content: "Learn Next.js" },
     { id: "task-2", content: "Install @hello-pangea/dnd" },
   ],
 },
 "column-2": {
   id: "column-2",
   title: "In Progress",
   tasks: [{ id: "task-3", content: "Build a Kanban Board" }],
 },
 "column-3": {
   id: "column-3",
   title: "Done",
   tasks: [{ id: "task-4", content: "Setup project structure" }],
 },
};


export default function KanbanBoard() {
 const [columns, setColumns] = useState(initialData);


 const onDragEnd = (result: DropResult) => {
   const { source, destination } = result;


   // dropped outside the list
   if (!destination) return;


   // same column
   if (source.droppableId === destination.droppableId) {
     const column = columns[source.droppableId];
     const newTasks = Array.from(column.tasks);
     const [movedTask] = newTasks.splice(source.index, 1);
     newTasks.splice(destination.index, 0, movedTask);


     setColumns({
       ...columns,
       [column.id]: {
         ...column,
         tasks: newTasks,
       },
     });
   } else {
     // moving to different column
     const sourceColumn = columns[source.droppableId];
     const destColumn = columns[destination.droppableId];
     const sourceTasks = Array.from(sourceColumn.tasks);
     const destTasks = Array.from(destColumn.tasks);
     const [movedTask] = sourceTasks.splice(source.index, 1);
     destTasks.splice(destination.index, 0, movedTask);


     setColumns({
       ...columns,
       [sourceColumn.id]: {
         ...sourceColumn,
         tasks: sourceTasks,
       },
       [destColumn.id]: {
         ...destColumn,
         tasks: destTasks,
       },
     });
   }
 };


 return (
   <div className="p-8 flex gap-4 overflow-x-auto">
     <DragDropContext onDragEnd={onDragEnd}>
       {Object.values(columns).map((column) => (
         <Droppable key={column.id} droppableId={column.id}>
           {(provided) => (
             <div
               {...provided.droppableProps}
               ref={provided.innerRef}
               className="bg-gray-100 p-4 rounded-md min-w-[250px] flex-shrink-0"
             >
               <h2 className="font-bold mb-4">{column.title}</h2>
               {column.tasks.map((task, index) => (
                 <Draggable key={task.id} draggableId={task.id} index={index}>
                   {(provided, snapshot) => (
                     <div
                       ref={provided.innerRef}
                       {...provided.draggableProps}
                       {...provided.dragHandleProps}
                       className={`p-3 mb-2 rounded shadow ${
                         snapshot.isDragging ? "bg-blue-200" : "bg-white"
                       }`}
                     >
                       {task.content}
                     </div>
                   )}
                 </Draggable>
               ))}
               {provided.placeholder}
             </div>
           )}
         </Droppable>
       ))}
     </DragDropContext>
   </div>
 );
}
