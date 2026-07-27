import { Contract } from './contract.model';

/** Miroir de com.tbart.foot_guessr.entities.Player */
export interface Player {
  id: number;
  firstname: string;
  lastname: string;
  /** Format ISO renvoyé par LocalDate, ex : '1972-06-23' */
  birthDate: string;
  position: string;
  nationality: string;
  retired: boolean;
  /** Absent tant qu'aucun contrat n'est rattaché au joueur */
  career?: Contract[] | null;
}
