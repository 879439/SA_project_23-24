// main.server.ts
import 'zone.js/dist/zone-node';
import { enableProdMode } from '@angular/core';
//import { renderModuleFactory } from '@angular/platform-server';
//import { AppServerModule } from './app/app.server.module'; 
import { ngExpressEngine } from '@nguniversal/express-engine';
import express from 'express';
import { join } from 'path';

export default function bootstrap() {
  // Your server-side logic here
}

// Enable production mode
enableProdMode();

// Express server
const app = express();

// Angular Express Engine
//app.engine('html', ngExpressEngine({
//    bootstrap: AppServerModule,
//}));

app.set('view engine', 'html');
app.set('views', join(__dirname, 'dist/flight-ticket-sales'));

// Serve static files
app.get('*.*', express.static(join(__dirname, 'dist/flight-ticket-sales')));

// All regular routes use the Universal engine
app.get('*', (req: express.Request, res: express.Response) => {
  // ...
});

// Start the server
const port = process.env['PORT'] || 4000;
app.listen(port, () => {
  console.log(`Node Express server listening on http://localhost:${port}`);
});
