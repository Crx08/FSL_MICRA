import type { Utente } from './Utente';
import type { CopiaLibro } from './CopiaLibro';

export interface Prestito {
  idPrestito: number;
  utente: Utente;
  copiaLibro: CopiaLibro;
  dataInizio: string;
  dataScadenza: string;
  dataRestituzione?: string | null;
}
