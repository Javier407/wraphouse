// src/app/layout/sidebar/sidebar.component.ts

import {
  ChangeDetectionStrategy, Component,
  EventEmitter, Input, Output, inject
} from '@angular/core';
import { AsyncPipe, NgClass } from '@angular/common';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { MotoService } from '../../core/services/moto.service';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [NgClass, AsyncPipe, RouterLink, RouterLinkActive],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css',
})
export class SidebarComponent {
  @Input()  open = false;
  @Output() linkClicked = new EventEmitter<void>();

  readonly motos$ = inject(MotoService).motos$;
}
