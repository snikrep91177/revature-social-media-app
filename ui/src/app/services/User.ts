import { UserProfile } from './Profile';

export interface IUser {
    password: string;
    username: string;
    user_id: number;
    email: string;
    firstName: string;
    lastName: string;
    occupation: string;
    birthdate: string;
    hobbies: string;
}
/*
	// private List<Post> posts;
	private String email;
	private String firstName;
	private String lastName;
	private String occupation;
	private Timestamp birthdate;
	private String hobbies;
	String imageLink;
*/
