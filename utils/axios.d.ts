import * as axios from 'axios';

declare module 'axios' {

  interface AxiosResponse<T = any> {
    code: number;
    data: T;
    message: string;

  }

  interface AxiosInstance {
    (config: AxiosRequestConfig): Promise<AxiosResponse>;
  }
}