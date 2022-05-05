import { HttpHeaders } from "@angular/common/http";

export class AppSettings {
  public static API_ENDPOINT='http://localhost:8081/api/v0';
  public static NO_AUTH_HEADER = new HttpHeaders({ 'no-auth': 'True' });
  public static JSON_HTTPOPTIONS = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  };
}
