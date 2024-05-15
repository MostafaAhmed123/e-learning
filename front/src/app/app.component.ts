import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  template: `
    <label style="color: white;">Amr Alaa / Mostafa Ahmed</label>
    <router-outlet></router-outlet>
  `,
  styles: [],
})
export class AppComponent {
  title = 'myapp';
}
