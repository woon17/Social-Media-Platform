import { User } from "./user";

export class Post {
  id!: number;
  caption: string | undefined;
  type: string | undefined;
  link!: string;
  views: number | undefined;
  user?: User;
  createdDate?: string

}
