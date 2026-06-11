// app/models/Libro.ts

export interface Libro {
    id?: number;         // Opzionale perché quando crei un nuovo libro l'ID lo genera il DB
    titolo: string;
    autore: string;
    isbn: string;
    annoPubblicazione: number;
    disponibile: boolean;
}