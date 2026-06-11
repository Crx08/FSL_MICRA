import type { Libro } from "./Libro";
import type { Utente } from "./Utente";

export interface Prestito {
    id?: number;
    libro: Libro;
    utente: Utente;
    dataInizio: string; // Gestite come stringhe ISO (YYYY-MM-DD)
    dataFine?: string;
    restituito: boolean;
}