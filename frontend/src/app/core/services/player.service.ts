import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';

import { environment } from '../../../environments/environment';
import { Player } from '../models';

@Injectable({ providedIn: 'root' })
export class PlayerService {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = `${environment.apiUrl}/players`;

  /** GET /api/players/random — `famousOnly` restreint le tirage aux joueurs connus. */
  getRandomPlayer(famousOnly: boolean): Observable<Player> {
    return this.http.get<Player>(`${this.baseUrl}/random`, { params: { famousOnly } });
  }

  /** GET /api/players/{id} */
  getPlayerById(id: number): Observable<Player> {
    return this.http.get<Player>(`${this.baseUrl}/${id}`);
  }
}
