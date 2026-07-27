/**
 * En dev, `/api` est redirigé vers http://localhost:8080 par proxy.conf.json
 * (voir angular.json > serve > development). Aucun CORS à configurer côté Spring.
 *
 * Si le front est un jour déployé sur un domaine différent du back,
 * remplacer par l'URL absolue de l'API, ex : 'https://api.foot-guessr.fr/api'.
 */
export const environment = {
  production: false,
  apiUrl: '/api',
};
