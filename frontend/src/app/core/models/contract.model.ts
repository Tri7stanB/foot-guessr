import { Club } from './club.model';
import { Player } from './player.model';

/** Miroir de com.tbart.foot_guessr.entities.Contract */
export interface Contract {
  id: number;
  club: Club;
  startYear: number;
  /** null tant que le joueur est encore au club */
  endYear: number | null;
}
