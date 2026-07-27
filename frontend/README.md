# Foot Guessr — frontend

Angular 19 (standalone components, signals), base minimale branchée sur l'API Spring Boot.

## Démarrer

```bash
npm install
npm start
```

Le front tourne sur http://localhost:4200. Il faut lancer le back en parallèle
(`cd ../backend && ./mvnw spring-boot:run`, port 8080).

Les appels vers `/api/**` sont redirigés vers `http://localhost:8080` par
[proxy.conf.json](proxy.conf.json) — donc rien à configurer côté CORS.

## Tests

```bash
npm test
```

## Structure

```
src/app/
  core/
    models/     interfaces TS miroir des entités JPA (Player, Club, Contract)
    services/   PlayerService : appels HTTP vers /api/players
  app.component.*   page unique, à faire évoluer en jeu
  app.config.ts     providers (router, HttpClient)
  app.routes.ts     routes (vide pour l'instant)
src/environments/
  environment.ts    apiUrl
```

## API disponible

| Méthode | URL                   | Retour   |
| ------- | --------------------- | -------- |
| GET     | `/api/players/random` | `Player` |
| GET     | `/api/players/{id}`   | `Player` |
