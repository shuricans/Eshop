import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {ImageService} from "../../service/image.service";

@Component({
  selector: '[app-carousel-picture]',
  templateUrl: './carousel-picture.component.html',
  styleUrls: ['./carousel-picture.component.scss']
})
export class CarouselPictureComponent implements OnInit {

  @Input() picId!: number;
  @Input() picAlt!: string;
  imageToShow: any;
  @Output() ready = new EventEmitter();
  @Input() isAllImagesReady!: boolean;

  constructor(private imageService: ImageService) {
  }

  ngOnInit(): void {
    this.getImageFromService();
  }

  private createImageFromBlob(image: Blob) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
      this.imageToShow = reader.result;
    }, false);
    if (image) {
      reader.readAsDataURL(image);
    }
  }

  getImageFromService() {
    this.imageService.getPicture(this.picId)
      .subscribe({
        next: (data) => {
          this.createImageFromBlob(data);
          this.ready.emit();
        },
        error: err => {
          console.error(err);
          return undefined;
        }
      });
  }
}
