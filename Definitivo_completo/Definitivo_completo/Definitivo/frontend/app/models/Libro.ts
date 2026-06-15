import type { Autore } from './Autore';

export interface Libro {
  codiceIsbn: string;
  titolo: string;
  annoPubblicazione?: number | null;
  autore: Autore | { id: number | string };
}
