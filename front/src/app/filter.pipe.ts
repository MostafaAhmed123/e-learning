import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filter'
})
export class FilterPipe implements PipeTransform {
  transform(courses: any[], searchText: string): any[] {
    if (!courses) {
      return [];
    }
    if (!searchText) {
      return courses;
    }
    searchText = searchText.toLowerCase();

    return courses.filter(course => {
      //when i get it from api  JSON.stringify(course).tolowercase().includes(searchText);
      for (let key in course) {
        if (course[key] && course[key].toString().toLowerCase().includes(searchText)) {
          return true;
        }
      }
      return false;
    });
  }

}
