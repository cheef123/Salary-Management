import { Component, OnInit } from '@angular/core';
import { HttpEventType, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { UploadCsvService } from '../upload.service';

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.css']
})
export class UploadComponent implements OnInit {
  selectedFiles: FileList;
  currentFile: File;
  progress = 0;
  message = '';
  fileInfos: Observable<any>;
  constructor(private uploadService: UploadCsvService) { }

  selectFile(event: any) {
    this.selectedFiles = event.target.files;
    this.message = " ";
  }

  upload() {
    this.progress = 0;
    this.currentFile = this.selectedFiles.item(0);
    this.uploadService.upload(this.currentFile).subscribe(
      event => {
        if (event.type === HttpEventType.UploadProgress) {
          this.progress = Math.round(100 * event.loaded / event.total);
        } else if (event instanceof HttpResponse) {
          this.message = event.body.message;
          console.log(event.body);
        }
      },

      err => {
        this.progress = 0;
        this.message = "Could not upload file! " + err.error.reason + " " + err.error.message;
        this.currentFile = undefined;
        console.log(err.error);
      });
    this.selectedFiles = undefined;
  }

  ngOnInit(): void {
  }

}
