import { IUser } from './User';
import { IRating } from './Rating';

export interface IPost {
    postId?: number;
    user_id: number;
    post: string;
    rating: number[];
}
