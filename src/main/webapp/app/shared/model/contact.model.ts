import { Moment } from 'moment';
import { IPurchase } from 'app/shared/model/purchase.model';
import { ISales } from 'app/shared/model/sales.model';

export interface IContact {
  id?: number;
  customerStatus?: boolean;
  supplierStatus?: boolean;
  code?: string;
  name?: string;
  phone1?: string;
  phone2?: string;
  email?: string;
  address?: string;
  city?: string;
  province?: string;
  postCode?: string;
  salesName?: string;
  createdBy?: string;
  createdDate?: Moment;
  lastModifiedBy?: string;
  lastModifiedDate?: Moment;
  recordStatusId?: number;
  suppliers?: IPurchase[];
  customers?: ISales[];
}

export class Contact implements IContact {
  constructor(
    public id?: number,
    public customerStatus?: boolean,
    public supplierStatus?: boolean,
    public code?: string,
    public name?: string,
    public phone1?: string,
    public phone2?: string,
    public email?: string,
    public address?: string,
    public city?: string,
    public province?: string,
    public postCode?: string,
    public salesName?: string,
    public createdBy?: string,
    public createdDate?: Moment,
    public lastModifiedBy?: string,
    public lastModifiedDate?: Moment,
    public recordStatusId?: number,
    public suppliers?: IPurchase[],
    public customers?: ISales[]
  ) {
    this.customerStatus = this.customerStatus || false;
    this.supplierStatus = this.supplierStatus || false;
  }
}
