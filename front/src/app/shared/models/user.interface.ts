export type Roles = 'ROLE_ADMIN' | 'ROLE_CONSUMER' | 'ROLE_CREATOR';

export interface User {
  email: string;
  password: string;
}

export interface UserResponse extends User {
  username: string;
  accessToken: number;
  role: Roles;
}
