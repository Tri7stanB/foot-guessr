import { Component, inject, signal } from '@angular/core';

import { Player } from './core/models';
import { PlayerService } from './core/services/player.service';

@Component({
  selector: 'app-root',
  imports: [],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent {
  private readonly playerService = inject(PlayerService);

  readonly player = signal<Player | null>(null);
  readonly error = signal<string | null>(null);

  loadRandomPlayer(): void {
    this.error.set(null);

    this.playerService.getRandomPlayer().subscribe({
      next: (player) => this.player.set(player),
      error: () => this.error.set("Impossible de joindre l'API (le back tourne-t-il sur :8080 ?)"),
    });
  }
}
