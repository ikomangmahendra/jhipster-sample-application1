export interface IBarang {
  id?: number;
  name?: string;
}

export class Barang implements IBarang {
  constructor(public id?: number, public name?: string) {}
}
