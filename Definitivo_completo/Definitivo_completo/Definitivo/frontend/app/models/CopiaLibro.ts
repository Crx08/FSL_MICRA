import type { Libro } from './Libro';

export interface CopiaLibro {
  idCopia: number;
  disponibile: boolean;
  statoConservazione: string;
  libro: Libro;
}
