import { Club } from './club.model';
import { Player } from './player.model';

/** Miroir de com.tbart.foot_guessr.entities.Contract */
export interface Contract {
  id: number;
  club: Club;
  startYear: number;
  /** null tant que le joueur est encore au club */
  endYear: number | null;
  /**
   * L'entité JPA porte une référence retour vers le joueur : elle est
   * sérialisée par l'API mais inutile ici (et source de cycles JSON).
   */
  player?: Player;
}
