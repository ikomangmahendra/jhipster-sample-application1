import { Moment } from 'moment';

export interface ITax {
  id?: number;
  code?: string;
  name?: string;
  taxRage?: number;
  description?: string;
  createdBy?: string;
  createdDate?: Moment;
  lastModifiedBy?: string;
  lastModifiedDate?: Moment;
  recordStatusId?: number;
}

export class Tax implements ITax {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public taxRage?: number,
    public description?: string,
    public createdBy?: string,
    public createdDate?: Moment,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Moment,
    public recordStatusId?: number
  ) {}
}
