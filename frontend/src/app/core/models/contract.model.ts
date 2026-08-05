import { Club } from './club.model';

/** Miroir de com.tbart.foot_guessr.dto.CareerStepDto */
export interface Contract {
  club: Club;
  startYear: number;
  /** null tant que le joueur est encore au club */
  endYear: number | null;
}
