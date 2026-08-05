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

  readonly infos = signal(new Set<string>());

  /** true = mode facile (joueurs connus), false = mode difficile (toute la base). */
  readonly famousOnly = signal(true);

  setMode(famousOnly: boolean): void {
    if (this.famousOnly() === famousOnly) {
      return;
    }
    this.famousOnly.set(famousOnly);
    this.loadRandomPlayer();
  }

  loadRandomPlayer(): void {
    this.error.set(null);
    this.verification.set(null);
    this.guess.set('');
    this.infos.set(new Set<string>());

    this.playerService.getRandomPlayer(this.famousOnly()).subscribe({
      next: (player) => this.player.set(player),
      error: (response) =>
        this.error.set(
          response.status === 404
            ? 'Aucun joueur disponible dans ce mode.'
            : "Impossible de joindre l'API.",
        ),
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
    
    if (this.acceptedAnswers(currentPlayer.name).includes(this.normalize(this.guess()))) {
      this.verification.set(`Bonne réponse !`);
    } else {
      this.verification.set('Ce n\'est pas le bon joueur.');
    }
  }

  /**
   * Nom complet, plus le nom de famille seul — c'est-à-dire tout ce qui suit le premier
   * espace, ce qui conserve les particules (« van Persie », « De Bruyne »).
   */
  private acceptedAnswers(name: string): string[] {
    const answers = [name];

    const firstSpace = name.indexOf(' ');
    if (firstSpace !== -1) {
      answers.push(name.slice(firstSpace + 1));
    }

    return answers.map((answer) => this.normalize(answer));
  }

  /** Minuscules, espaces superflus et accents retirés, pour accepter « pele » comme « Pelé ». */
  private normalize(value: string): string {
    return value
      .trim()
      .toLowerCase()
      .normalize('NFD')
      .replace(/\p{Diacritic}/gu, '');
  }

  isInfoRevealed(info: string): boolean {
    return this.infos().has(info);
  }

  revealInfo(info: string): void {
    this.infos.update((currentInfos) => new Set([...currentInfos, info])); 
  }
}
