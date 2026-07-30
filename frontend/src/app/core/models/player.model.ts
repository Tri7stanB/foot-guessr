import { Contract } from './contract.model';
import { List } from 'immutable';

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
  career?: List<Contract>;
}
