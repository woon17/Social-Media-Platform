import { User } from "./user";

export class Post {
  id: number | undefined;
  caption: string | undefined;
  type: string | undefined;
  link: string | undefined;
  views: number | undefined;
  user?: User
}
