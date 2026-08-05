import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';

import { Player } from '../models';
import { PlayerService } from './player.service';

describe('PlayerService', () => {
  let service: PlayerService;
  let httpMock: HttpTestingController;

  const zidane: Player = {
    name: 'Zinedine Zidane',
    birthDate: '1972-06-23',
    position: 'Milieu',
    nationality: 'France',
    retired: true,
    career: [],
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });

    service = TestBed.inject(PlayerService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => httpMock.verify());

  it('appelle /api/players/random et renvoie le joueur', () => {
    let result: Player | undefined;
    service.getRandomPlayer(true).subscribe((player) => (result = player));

    const req = httpMock.expectOne('/api/players/random?famousOnly=true');
    expect(req.request.method).toBe('GET');
    req.flush(zidane);

    expect(result).toEqual(zidane);
  });

  it('appelle /api/players/{id} et renvoie le joueur', () => {
    let result: Player | undefined;
    service.getPlayerById(1).subscribe((player) => (result = player));

    const req = httpMock.expectOne('/api/players/1');
    expect(req.request.method).toBe('GET');
    req.flush(zidane);

    expect(result).toEqual(zidane);
  });
});
