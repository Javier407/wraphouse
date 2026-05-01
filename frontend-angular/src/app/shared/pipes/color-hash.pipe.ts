// src/app/shared/pipes/color-hash.pipe.ts

import { Pipe, PipeTransform } from '@angular/core';

@Pipe({ name: 'colorHash', standalone: true, pure: true })
export class ColorHashPipe implements PipeTransform {
  transform(name: string | null | undefined): string {
    if (!name) return 'var(--border-mid)';
    // Try native browser CSS color recognition
    const canvas = document.createElement('canvas');
    canvas.width = canvas.height = 1;
    const ctx = canvas.getContext('2d')!;
    ctx.fillStyle = '#888888';
    ctx.fillStyle = name;
    const resolved = ctx.fillStyle;
    return resolved === '#888888' ? this._fromHash(name) : name;
  }

  private _fromHash(str: string): string {
    let hash = 0;
    for (let i = 0; i < str.length; i++) {
      hash = str.charCodeAt(i) + ((hash << 5) - hash);
    }
    return `hsl(${Math.abs(hash) % 360}, 55%, 45%)`;
  }
}
