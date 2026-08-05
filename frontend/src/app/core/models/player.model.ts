import { Contract } from './contract.model';

/** Miroir de com.tbart.foot_guessr.dto.PlayerDto */
export interface Player {
  /** Nom d'usage : la réponse attendue */
  name: string;
  /** Format ISO renvoyé par LocalDate, ex : '1972-06-23' */
  birthDate: string;
  position: string;
  nationality: string;
  retired: boolean;
  career: Contract[];
}
