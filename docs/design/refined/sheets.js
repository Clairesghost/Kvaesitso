/* helpers for sheets (annotation colour is coral so it is never mistaken for the design) */
const CORAL = '#ff8f7a';
function wRow(state, scheme, title, temp, word, noteLines, deco) {
  const sc = SCH[scheme], P = 'w' + (UID++) + '_';
  let s = `<svg xmlns="http://www.w3.org/2000/svg" width="1600" height="330" viewBox="0 0 1600 330"><defs>${defs(sc)}</defs>`;
  s += `<rect width="1600" height="330" rx="26" fill="url(#@paint)"/><rect width="1600" height="330" rx="26" fill="#000" filter="url(#@mot)" opacity=".1"/>`;
  s += `<g filter="url(#@sh)">${metal(rr(60, 70, 700, 180, 90), 'cV')}</g>` + rr(62, 72, 696, 176, 88)('fill="none" stroke="#fff" stroke-width="2.5" opacity=".7"') + rr(60, 70, 700, 180, 90)(`fill="none" stroke="${sc.ink}" stroke-width="3" opacity=".7"`);
  s += rr(300, 88, 440, 144, 72)('fill="url(#@cVr)"') + rr(306, 94, 428, 132, 66)('fill="url(#@plate)"') + rr(306, 94, 428, 132, 66)('fill="none" stroke="#000" stroke-width="4" opacity=".6"');
  s += `<text x="344" y="182" font-family="CG" font-weight="600" font-size="88" fill="${sc.plateFg}">${temp}</text><text x="700" y="172" text-anchor="end" font-family="CSC" font-weight="600" font-size="44" letter-spacing="2" fill="${sc.plateFg}">${word}</text>`;
  s += `<g transform="translate(160 160)">${lensGroup(sc, state, 106)}</g>`;
  s += `<g transform="translate(1010 165)">${lensGroup(sc, state, 130, 'b')}<g transform="scale(1.3)">${deco}</g></g>`;
  noteLines.forEach((t, i) => s += `<text x="1190" y="${96 + i * 32}" font-family="Lato" font-size="22" fill="${i === 0 ? '#fff' : '#e8efee'}" font-weight="${i === 0 ? 700 : 400}">${t}</text>`);
  return (s + '</svg>').replace(/@/g, P);
}
function callout(x, y, text, w, tx, ty) {
  /* tx,ty = point the little line goes to */
  let s = '';
  if (tx != null) s += `<path d="M${x + w / 2} ${y + 22}L${tx} ${ty}" stroke="${CORAL}" stroke-width="4"/><circle cx="${tx}" cy="${ty}" r="8" fill="${CORAL}"/>`;
  return s + `<rect x="${x}" y="${y - 24}" width="${w}" height="48" rx="24" fill="#1b1d20" stroke="${CORAL}" stroke-width="3"/><text x="${x + w / 2}" y="${y + 11}" text-anchor="middle" font-family="Lato" font-weight="700" font-size="29" fill="${CORAL}">${text}</text>`;
}
function iconRow(scheme, style, r, names, label) {
  const sc = SCH[scheme], P = 'i' + (UID++) + '_';
  const sp = 125, W = names.length * sp + 40;
  let s = `<svg xmlns="http://www.w3.org/2000/svg" width="${W}" height="215" viewBox="0 0 ${W} 215"><defs>${defs(sc)}</defs>`;
  s += `<rect width="${W}" height="215" rx="20" fill="${sc.smoke.length ? (sc.night || scheme === 'a' ? sc.sc.dark : sc.sc.mid) : '#222'}"/><rect width="${W}" height="215" rx="20" fill="url(#@sky)" opacity="${scheme === 'd' ? .9 : .0}"/>`;
  if (scheme === 'd') s += `<rect width="${W}" height="215" rx="20" fill="#d9e6df"/>`;
  names.forEach((n, i) => s += `<g transform="translate(${20 + sp / 2 + i * sp} 85)">${icon(sc, n, r, style)}<text y="${r + 38}" text-anchor="middle" font-family="CSC" font-weight="600" font-size="25" letter-spacing="1.2" fill="${sc.label}">${n}</text></g>`);
  return (s + '</svg>').replace(/@/g, P);
}
