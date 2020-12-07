import { Moment } from 'moment';

export interface IUnit {
  id?: number;
  code?: string;
  name?: string;
  description?: string;
  baseUnitStatus?: boolean;
  createdBy?: string;
  createdDate?: Moment;
  lastModifiedBy?: string;
  lastModifiedDate?: Moment;
  recordStatusId?: number;
}

export class Unit implements IUnit {
  constructor(
    public id?: number,
    public code?: string,
    public name?: string,
    public description?: string,
    public baseUnitStatus?: boolean,
    public createdBy?: string,
    public createdDate?: Moment,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Moment,
    public recordStatusId?: number
  ) {
    this.baseUnitStatus = this.baseUnitStatus || false;
  }
}
