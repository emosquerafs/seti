export const environment = {
  production: false,
  keycloak: {
    issuer: 'http://localhost:8080',
    realm: 'SETI',
    clientId: 'SETI'
  },
  checkLoginInterval: 300,
  hostName: 'http://localhost:4200',
  api_gateway: 'http://localhost:8081'
  
};
