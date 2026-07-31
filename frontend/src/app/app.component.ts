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
  readonly verification = signal<string | null>(null);
  readonly guess = signal('');  

  loadRandomPlayer(): void {
    this.error.set(null);

    this.playerService.getRandomPlayer().subscribe({
      next: (player) => this.player.set(player),
      error: () => this.error.set("Impossible de joindre l'API (le back tourne-t-il sur :8080 ?)"),
    });
  }

  onGuessInput(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.guess.set(input.value);
  }

  submitGuess(): void {
    const currentPlayer = this.player();
    if (!currentPlayer) {
      this.error.set('Aucun joueur chargé pour le moment.');
      return;
    }
    
    const guessValue = this.guess().trim().toLowerCase();
    const actualName = `${currentPlayer.firstname} ${currentPlayer.lastname}`.toLowerCase();

    if (guessValue === actualName || guessValue === currentPlayer.lastname.toLowerCase()) {
      this.verification.set(`Bonne réponse !`);
    } else {
      this.verification.set('Ce n\'est pas le bon joueur.');
    }
  }
}
