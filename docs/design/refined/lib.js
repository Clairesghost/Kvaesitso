/* Refined handheld (round 4): drawing library. Pictures only. Every id has an "@" that becomes a per-picture prefix. */
const FONTS = `@font-face{font-family:CG;src:url(fonts/CormorantGaramond-VF.ttf);font-weight:300 700}
@font-face{font-family:CSC;src:url(fonts/CormorantSC-SemiBold.ttf);font-weight:600}
@font-face{font-family:CSCm;src:url(fonts/CormorantSC-Medium.ttf);font-weight:500}
@font-face{font-family:Lato;src:url(../fonts/Lato-Regular.ttf);font-weight:400}
@font-face{font-family:Lato;src:url(../fonts/Lato-Bold.ttf);font-weight:700}`;
document.head.insertAdjacentHTML('beforeend', '<style>' + FONTS + 'body{margin:0;background:#16171a;font-family:Lato,sans-serif}svg{display:block}</style>');
let UID = 0;

const SCH = {
  a: { name: 'Chrome and aged brass, dusty teal paint', night: false,
    chrome: ['#fbfcfc', '#d9dee0', '#9ea8ad', '#eef1f2', '#bcc4c8', '#858f95'],
    brass: ['#f1dfae', '#cdac66', '#a98443', '#e0c27f', '#8c6b33', '#66491f'],
    paint1: '#69979a', paint2: '#41696d', ink: '#15282b', paper: '#efe7d3', paper2: '#e0d6bc', accent: '#8a3b47',
    plate: '#1a3033', plate2: '#0f1f22', plateFg: '#f1e6c8', enam1: '#477277', enam2: '#244449', glyph: '#f0dca6',
    smoke: '#14282b', smokeA: .42, label: '#f5ecd2', frost: 'rgba(246,240,222,.46)', hands: '#17292c',
    trayA: '#1d3235', trayB: '#0f2023', trayLabel: '#f5ecd2', glow: null, wash: .16,
    sc: { sky1: '#a9c9c6', sky2: '#eee3c6', far: '#a5c2bd', mid: '#76a09d', near: '#4a7478', warm: '#d2a350', rose: '#c78e89', cream: '#f4ead0', dark: '#2c4e52', win: '#f4df9c', winOp: .4 } },
  b: { name: 'Same, at night, warm lamp glow', night: true,
    chrome: ['#d3d9dc', '#939da3', '#5b656c', '#b1bac0', '#6f7a81', '#3f484e'],
    brass: ['#ebcd88', '#b98f46', '#80602a', '#d0a55b', '#6c4d1e', '#43300f'],
    paint1: '#2f5357', paint2: '#16303a', ink: '#0b191c', paper: '#e6d8b6', paper2: '#cebe98', accent: '#8a3b47',
    plate: '#0d1d21', plate2: '#061013', plateFg: '#ecdcb2', enam1: '#264549', enam2: '#112a2e', glyph: '#f2d38c',
    smoke: '#07141a', smokeA: .5, label: '#ecdcb0', frost: 'rgba(24,46,52,.52)', hands: '#f0d9a0',
    trayA: '#0e1f23', trayB: '#050e11', trayLabel: '#ecdcb0', glow: '#ffb458', wash: .05,
    sc: { sky1: '#15303c', sky2: '#42605f', far: '#2b4b54', mid: '#1d3a42', near: '#12282e', warm: '#e4ae5a', rose: '#8c5b64', cream: '#d9c99c', dark: '#0d2025', win: '#f6cc74', winOp: .95 } },
  c: { name: 'Graphite and rose gold', night: true,
    chrome: ['#aab0b7', '#767c85', '#4a5059', '#8d939b', '#575d66', '#30343a'],
    brass: ['#f7d8c8', '#dba892', '#b57e6d', '#eabfa9', '#966253', '#6c403a'],
    paint1: '#564f65', paint2: '#2f2b3b', ink: '#16141d', paper: '#ece2da', paper2: '#dacdc4', accent: '#4a6a5c',
    plate: '#1b1923', plate2: '#0e0d13', plateFg: '#f3e6de', enam1: '#44414f', enam2: '#262430', glyph: '#f3cdb9',
    smoke: '#1a1822', smokeA: .46, label: '#f4e7df', frost: 'rgba(236,224,218,.40)', hands: '#2a2735',
    trayA: '#1d1b26', trayB: '#0e0d14', trayLabel: '#f4e7df', glow: null, wash: .10,
    sc: { sky1: '#8e87a5', sky2: '#e6d1ca', far: '#aaa2bb', mid: '#7e7798', near: '#4e4860', warm: '#dba890', rose: '#c78f9b', cream: '#f1e5dd', dark: '#2e2a3a', win: '#f6dcbd', winOp: .45 } },
  d: { name: 'Ivory enamel and brass', night: false,
    chrome: ['#fffefa', '#f1e9d6', '#d3c8aa', '#f8f2e3', '#e1d7bc', '#b9ad8b'],
    brass: ['#f3e2b2', '#cfb06a', '#ac8847', '#e2c683', '#8e6e36', '#69501f'],
    paint1: '#b9cfc7', paint2: '#90b1aa', ink: '#223533', paper: '#fbf6e8', paper2: '#efe6cf', accent: '#7b3a45',
    plate: '#25413f', plate2: '#16292a', plateFg: '#f6eed6', enam1: '#fbf6e6', enam2: '#e5dbbf', glyph: '#2b4d50',
    smoke: '#f6efdc', smokeA: .5, label: '#23383a', frost: 'rgba(255,251,236,.50)', hands: '#223533',
    trayA: '#d8e4dc', trayB: '#bdd0c7', trayLabel: '#23383a', glow: null, wash: .30,
    sc: { sky1: '#cfe1dd', sky2: '#f7efda', far: '#c6d8d2', mid: '#a3c0ba', near: '#74a09d', warm: '#dcb46c', rose: '#d9a49f', cream: '#fbf4e0', dark: '#4b7172', win: '#f7e4a9', winOp: .35 } }
};
const LAY = {
  i: { stripY: 200, stripH: 180, lensR: 106, dialCY: 945, dialR: 468, placY: 1475, placH: 300, trayY: 1845, trayH: 380, discR: 72 },
  ii: { stripY: 196, stripH: 150, lensR: 90, dialCY: 930, dialR: 490, placY: 1500, placH: 205, trayY: 1790, trayH: 410, discR: 80 }
};
const LABELS = ['Web', 'Music', 'Maps', 'Mail', 'Notes', 'Photos', 'Calendar', 'Calc', 'Settings', 'Files', 'Contacts', 'Books'];
const PINNED = ['Phone', 'Messages', 'Camera'];

/* ---------- small helpers ---------- */
const lg = (id, x1, y1, x2, y2, st) => `<linearGradient id="@${id}" x1="${x1}" y1="${y1}" x2="${x2}" y2="${y2}">${st.map(s => `<stop offset="${s[0]}" stop-color="${s[1]}"${s[2] != null ? ` stop-opacity="${s[2]}"` : ''}/>`).join('')}</linearGradient>`;
const rg = (id, st, extra = 'cx="0.5" cy="0.5" r="0.5"') => `<radialGradient id="@${id}" ${extra}>${st.map(s => `<stop offset="${s[0]}" stop-color="${s[1]}"${s[2] != null ? ` stop-opacity="${s[2]}"` : ''}/>`).join('')}</radialGradient>`;
const stV = c => [[0, c[0]], [.22, c[1]], [.47, c[3]], [.53, c[2]], [.8, c[4]], [1, c[5]]];
const stH = c => [[0, c[4]], [.07, c[0]], [.24, c[1]], [.5, c[3]], [.76, c[1]], [.93, c[0]], [1, c[5]]];
const rrD = (x, y, w, h, r) => `M${x + r} ${y}H${x + w - r}A${r} ${r} 0 0 1 ${x + w} ${y + r}V${y + h - r}A${r} ${r} 0 0 1 ${x + w - r} ${y + h}H${x + r}A${r} ${r} 0 0 1 ${x} ${y + h - r}V${y + r}A${r} ${r} 0 0 1 ${x + r} ${y}Z`;
const ringD = (r1, r2) => `M${r2} 0A${r2} ${r2} 0 1 0 ${-r2} 0A${r2} ${r2} 0 1 0 ${r2} 0ZM${r1} 0A${r1} ${r1} 0 1 1 ${-r1} 0A${r1} ${r1} 0 1 1 ${r1} 0Z`;
const rr = (x, y, w, h, r) => a => `<rect x="${x}" y="${y}" width="${w}" height="${h}" rx="${r}" ${a}/>`;
const ringS = (r1, r2) => a => `<path d="${ringD(r1, r2)}" fill-rule="evenodd" ${a}/>`;
const circS = r => a => `<circle r="${r}" ${a}/>`;
const pathS = d => a => `<path d="${d}" ${a}/>`;
/* a metal part: gradient base, brushed streaks light and dark, optional patina */
function metal(shape, grad, o = {}) {
  let s = shape(`fill="url(#@${grad})"`);
  if (!o.smooth) {
    s += shape(`fill="#000" filter="url(#@${o.iso ? 'gl' : 'bl'})" opacity="${o.k || .5}"`);
    s += shape(`fill="#000" filter="url(#@${o.iso ? 'gd' : 'bd'})" opacity="${o.k || .5}"`);
  }
  if (o.patina) s += shape(`fill="#000" filter="url(#@pat)" opacity=".2"`);
  return s;
}
function hash(a, b) { const v = Math.sin(a * 12.9898 + b * 78.233) * 43758.5453; return v - Math.floor(v); }

function defs(sc) {
  const c = sc.chrome, b = sc.brass, k = sc.sc;
  const filt = (id, freq, oct, seed, mat, extra = '') => `<filter id="@${id}" x="0" y="0" width="100%" height="100%" color-interpolation-filters="sRGB"><feTurbulence type="fractalNoise" baseFrequency="${freq}" numOctaves="${oct}" seed="${seed}"/><feColorMatrix values="${mat}"/><feComposite in2="SourceGraphic" operator="in"/></filter>`;
  return `
${lg('cV', 0, 0, 0, 1, stV(c))}${lg('cVr', 0, 1, 0, 0, stV(c))}${lg('cH', 0, 0, 1, 0, stH(c))}${lg('cD', 0, 0, 1, 1, stV(c))}
${lg('bV', 0, 0, 0, 1, stV(b))}${lg('bD', 0, 0, 1, 1, stV(b))}${lg('bDr', 1, 1, 0, 0, stV(b))}${lg('bH', 0, 0, 1, 0, stH(b))}
${lg('paint', 0, 0, 0, 1, [[0, sc.paint1], [1, sc.paint2]])}
${lg('paper', 0, 0, 0, 1, [[0, sc.paper], [1, sc.paper2]])}
${lg('plate', 0, 0, 0, 1, [[0, sc.plate], [1, sc.plate2]])}
${lg('tray', 0, 0, 0, 1, [[0, sc.trayB], [.35, sc.trayA], [1, sc.trayA]])}
${lg('sky', 0, 0, 0, 1, [[0, k.sky1], [.62, k.sky2], [1, k.sky2]])}
${lg('gnd', 0, 0, 0, 1, [[0, k.near], [1, k.dark]])}
${rg('enam', [[0, sc.enam1], [1, sc.enam2]], 'cx="0.4" cy="0.3" r="0.8"')}
${rg('wash', [[0, '#fff', sc.wash], [1, '#fff', 0]], 'cx="0.28" cy="0.12" r="0.8"')}
${rg('edge', [[.74, '#000', 0], [1, '#000', .5]])}
${rg('edgeS', [[.8, '#000', 0], [1, '#000', .55]])}
${rg('sunGl', [[0, k.cream, .95], [1, k.cream, 0]])}
${rg('glow', [[0, sc.glow || '#fff', .55], [1, sc.glow || '#fff', 0]])}
${rg('hl', [[0, '#fff', .75], [1, '#fff', 0]])}
${lg('envl', 0, 0, 0, 1, [[0, k.cream], [.6, '#fff', .9], [1, k.rose]])}
${filt('bl', '0.0016 0.75', 2, 4, '0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 2.6 0 0 0 -1.3')}
${filt('bd', '0.0016 0.75', 2, 4, '0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 -2.6 0 0 0 1.3')}
${filt('gl', '0.9', 2, 7, '0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 2.2 0 0 0 -1.1')}
${filt('gd', '0.9', 2, 7, '0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 -2.2 0 0 0 1.1')}
${filt('pat', '0.014', 3, 9, '0 0 0 0 .22 0 0 0 0 .26 0 0 0 0 .1 0 3.2 0 0 -1.25')}
${filt('mot', '0.007', 3, 2, '0 0 0 0 1 0 0 0 0 1 0 0 0 0 1 2.4 0 0 0 -1.15')}
${filt('motd', '0.007', 3, 5, '0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 -2.4 0 0 0 1.15')}
<filter id="@wob" x="-5%" y="-5%" width="110%" height="110%"><feTurbulence type="fractalNoise" baseFrequency="0.035" numOctaves="2" seed="3" result="n"/><feDisplacementMap in="SourceGraphic" in2="n" scale="5"/><feGaussianBlur stdDeviation="0.5"/></filter>
<filter id="@b1" x="-10%" y="-10%" width="120%" height="120%"><feGaussianBlur stdDeviation="1.2"/></filter>
<filter id="@b3" x="-20%" y="-20%" width="140%" height="140%"><feGaussianBlur stdDeviation="3.5"/></filter>
<filter id="@b8" x="-10%" y="-10%" width="120%" height="120%"><feGaussianBlur stdDeviation="9"/></filter>
<filter id="@b30" x="-50%" y="-50%" width="200%" height="200%"><feGaussianBlur stdDeviation="30"/></filter>
<filter id="@sh" x="-20%" y="-20%" width="140%" height="150%"><feDropShadow dx="0" dy="14" stdDeviation="14" flood-color="#000" flood-opacity=".5"/></filter>
<filter id="@sh2" x="-30%" y="-30%" width="160%" height="170%"><feDropShadow dx="0" dy="4" stdDeviation="4" flood-color="#000" flood-opacity=".55"/></filter>
<filter id="@gw" x="-50%" y="-50%" width="200%" height="200%"><feGaussianBlur stdDeviation="3" result="b"/><feMerge><feMergeNode in="b"/><feMergeNode in="SourceGraphic"/></feMerge></filter>
`;
}

/* ---------- glyphs: 48 box, centred, stroke only ---------- */
const GL = {
  Phone: '<path d="M-15 -14C-17 -8 -9 12 12 17C16 18 18 14 17 12L11 7L6 11C0 8 -6 2 -8 -3L-4 -8L-10 -15C-12 -17 -14 -16 -15 -14Z"/>',
  Messages: '<path d="M-18 -14H18V8H-3L-12 16V8H-18Z"/><path d="M-9 -5H9M-9 1H3"/>',
  Camera: '<path d="M-19 -10H-10L-6 -16H6L10 -10H19V15H-19Z"/><circle cx="0" cy="3" r="7"/>',
  Web: '<circle r="17"/><ellipse rx="7" ry="17"/><path d="M-17 0H17M-14 -9H14M-14 9H14"/>',
  Music: '<path d="M-6 12V-14L12 -18V8"/><circle cx="-10" cy="12" r="5"/><circle cx="8" cy="8" r="5"/>',
  Maps: '<path d="M0 18C-12 4 -14 -2 -14 -7A14 14 0 0 1 14 -7C14 -2 12 4 0 18Z"/><circle cy="-7" r="5"/>',
  Mail: '<rect x="-18" y="-12" width="36" height="24" rx="2"/><path d="M-18 -12L0 3L18 -12"/>',
  Notes: '<path d="M-12 -18H8L14 -12V18H-12Z"/><path d="M-6 -4H8M-6 3H8M-6 10H2"/>',
  Photos: '<rect x="-18" y="-14" width="36" height="28" rx="2"/><path d="M-18 10L-6 -2L2 6L8 0L18 10"/><circle cx="9" cy="-6" r="3"/>',
  Calendar: '<rect x="-17" y="-14" width="34" height="31" rx="2"/><path d="M-17 -5H17M-8 -18V-10M8 -18V-10"/><path d="M-9 3H-8M0 3H1M9 3H10M-9 10H-8M0 10H1" stroke-width="4"/>',
  Calc: '<rect x="-13" y="-18" width="26" height="36" rx="3"/><rect x="-8" y="-13" width="16" height="7"/><path d="M-7 3H-6M0 3H1M7 3H8M-7 10H-6M0 10H1M7 10H8" stroke-width="4"/>',
  Settings: '<circle r="10"/><circle r="4"/><path d="M0 -17V-12M0 12V17M-17 0H-12M12 0H17M-12 -12L-9 -9M9 9L12 12M-12 12L-9 9M9 -9L12 -12" stroke-width="4"/>',
  Files: '<path d="M-18 -12H-6L-2 -7H18V14H-18Z"/><path d="M-18 -3H18"/>',
  Contacts: '<circle cy="-7" r="7"/><path d="M-16 16C-16 5 -9 2 0 2C9 2 16 5 16 16Z"/>',
  Books: '<path d="M0 -12C-6 -16 -12 -16 -18 -14V14C-12 12 -6 12 0 16C6 12 12 12 18 14V-14C12 -16 6 -16 0 -12Z"/><path d="M0 -12V16"/>'
};

/* an icon at the origin with disc radius r. style A brass-rimmed enamel, B engraved brass, C glass dimple */
function icon(sc, name, r, style, o = {}) {
  const k = r / 50, g = GL[name];
  const glow = sc.glow ? ' filter="url(#@gw)"' : '';
  let s = `<g${o.press ? ` transform="scale(.94)"` : ''}><g filter="url(#@sh2)">`;
  if (style === 'A') {
    s += `<circle r="${r + 4}" fill="url(#@bD)"/><circle r="${r + 4}" fill="#000" filter="url(#@gl)" opacity=".25"/>`;
    s += `<circle r="${r - 1.5}" fill="#1a1208" opacity=".55"/><circle r="${r - 3.5}" fill="url(#@enam)"/>`;
    s += `<path d="M${-r * .62} ${-r * .38}A${r * .75} ${r * .75} 0 0 1 ${r * .62} ${-r * .38}A${r * .8} ${r * .5} 0 0 0 ${-r * .62} ${-r * .38}Z" fill="#fff" opacity=".16"/>`;
    s += `</g><g transform="scale(${k})" fill="none" stroke="${sc.glyph}" stroke-width="3" stroke-linecap="round" stroke-linejoin="round"${glow}>${g}</g>`;
  } else if (style === 'B') {
    s += `<circle r="${r + 1}" fill="url(#@bD)"/><circle r="${r + 1}" fill="#000" filter="url(#@gl)" opacity=".3"/><circle r="${r + 1}" fill="#000" filter="url(#@pat)" opacity=".2"/>`;
    s += `<circle r="${r - 6}" fill="none" stroke="#fff" stroke-width="1.4" opacity=".45"/><circle r="${r - 7.5}" fill="none" stroke="#3d2a0c" stroke-width="1.2" opacity=".45"/>`;
    s += `</g><g transform="scale(${k})" fill="none" stroke-linecap="round" stroke-linejoin="round"><g transform="translate(0.9 1.4)" stroke="#fff" stroke-opacity=".55" stroke-width="3.2">${g}</g><g stroke="#2a1d0a" stroke-opacity=".88" stroke-width="3.2">${g}</g></g>`;
  } else {
    s += `<circle r="${r + 2}" fill="url(#@bDr)"/><circle r="${r - 1.5}" fill="${sc.night || sc.smokeA && sc.label[1] > '9' ? '#0c1416' : '#243436'}"/>`;
    s += `<circle r="${r - 1.5}" fill="url(#@edgeS)"/><circle r="${r - 1.5}" fill="none" stroke="#000" stroke-width="3" opacity=".35"/>`;
    s += `</g><g transform="scale(${k})" fill="none" stroke="${sc.night ? sc.glyph : '#f0dca6'}" stroke-width="2.2" stroke-linecap="round" stroke-linejoin="round"${glow}>${g}</g>`;
  }
  return s + '</g>';
}

/* ---------- painted city scene, centred, covers -430..430 ---------- */
function sceneArt(sc, night) {
  const c = sc.sc;
  const tw = (arr, col, base, op, win) => arr.map(([x, w, h, sp], i) => {
    let t = `<rect x="${x}" y="${base - h}" width="${w}" height="${h + 420}" fill="${col}" opacity="${op}"/>`;
    if (w > 30) t += `<rect x="${x + 6}" y="${base - h - 9}" width="${w - 12}" height="10" fill="${col}" opacity="${op}"/>`;
    if (sp) t += `<path d="M${x + w / 2 - 3} ${base - h} L${x + w / 2} ${base - h - sp} L${x + w / 2 + 3} ${base - h}Z" fill="${col}" opacity="${op}"/>`;
    if (win) for (let yy = base - h + 12; yy < base - 8; yy += 15) for (let xx = x + 7; xx < x + w - 8; xx += 11) if (hash(xx, yy) > .55) t += `<rect x="${xx}" y="${yy}" width="4" height="6" fill="${c.win}" opacity="${c.winOp * (.5 + hash(yy, xx) * .5)}"/>`;
    return t;
  }).join('');
  const far = [[-430, 50, 120], [-380, 38, 190, 30], [-340, 46, 150], [-292, 32, 235, 40], [-258, 52, 140], [-204, 42, 180], [-160, 34, 112], [-122, 48, 205, 36], [-72, 38, 150], [-32, 54, 118], [24, 34, 215, 34], [60, 50, 150], [112, 38, 190], [150, 46, 132], [194, 30, 245, 42], [226, 52, 160], [280, 42, 122], [324, 48, 205, 28], [374, 38, 150], [414, 40, 110]];
  const mid = [[-430, 74, 178], [-352, 56, 262, 36], [-292, 62, 192], [-228, 84, 132], [-136, 40, 100], [118, 72, 205], [194, 58, 285, 40], [254, 78, 172], [336, 70, 236, 30], [410, 40, 140]];
  let s = `<g filter="url(#@wob)">`;
  s += `<rect x="-440" y="-440" width="880" height="880" fill="url(#@sky)"/>`;
  if (night) {
    s += `<circle cx="150" cy="-175" r="70" fill="url(#@sunGl)" opacity=".55"/><circle cx="150" cy="-175" r="24" fill="${c.cream}"/><circle cx="162" cy="-182" r="21" fill="${c.sky1}"/>`;
    for (let i = 0; i < 16; i++) s += `<circle cx="${-380 + hash(i, 1) * 760}" cy="${-380 + hash(i, 2) * 300}" r="${1 + hash(i, 3) * 1.6}" fill="${c.cream}" opacity=".8"/>`;
  } else {
    s += `<circle cx="170" cy="-120" r="190" fill="url(#@sunGl)" opacity=".9"/><circle cx="170" cy="-120" r="30" fill="#fff" opacity=".55"/>`;
    s += `<ellipse cx="-160" cy="-80" rx="120" ry="14" fill="#fff" opacity=".35" filter="url(#@b8)"/><ellipse cx="60" cy="-250" rx="150" ry="12" fill="#fff" opacity=".3" filter="url(#@b8)"/>`;
  }
  s += tw(far, c.far, 100, night ? .9 : .85, night);
  s += tw(mid, c.mid, 112, 1, true);
  /* elevated roadway */
  s += `<path d="M-430 62C-250 36 -120 70 20 52S300 30 430 58" fill="none" stroke="${c.cream}" stroke-width="5" opacity=".35"/>`;
  for (let x = -400; x < 430; x += 62) s += `<path d="M${x} 62V120" stroke="${c.cream}" stroke-width="3" opacity=".25"/>`;
  /* airship */
  s += `<g transform="translate(-96 -196) rotate(-7) scale(.8)"><ellipse rx="78" ry="26" fill="url(#@envl)"/><path d="M-60 -14C-20 -24 30 -24 60 -14" fill="none" stroke="${c.warm}" stroke-width="5" opacity=".9"/><path d="M-70 -6L-96 -26L-92 -2ZM-70 6L-96 26L-92 2Z" fill="${c.rose}"/><rect x="-16" y="26" width="32" height="10" rx="4" fill="${c.dark}"/><path d="M-10 26V22M10 26V22" stroke="${c.dark}" stroke-width="2"/></g>`;
  /* ground and road */
  s += `<rect x="-440" y="120" width="880" height="330" fill="url(#@gnd)"/>`;
  s += `<path d="M-440 176H440V262H-440Z" fill="${c.dark}"/><path d="M-440 176H440" stroke="${c.cream}" stroke-width="4" opacity=".7"/>`;
  for (let x = -420; x < 430; x += 70) s += `<rect x="${x}" y="217" width="34" height="4" fill="${c.cream}" opacity=".7"/>`;
  if (night) for (const lx of [-190, 190]) s += `<path d="M${lx} 176V120" stroke="${c.dark}" stroke-width="4"/><circle cx="${lx}" cy="116" r="40" fill="url(#@glow)"/><circle cx="${lx}" cy="116" r="6" fill="#ffe4a8"/>`;
  /* classic car */
  s += `<g transform="translate(6 205) scale(.82)"><ellipse cx="0" cy="26" rx="82" ry="7" fill="#000" opacity=".3"/><path d="M-70 10C-70 -4 -62 -8 -50 -10L-32 -12C-24 -26 -8 -32 12 -32C32 -32 46 -24 54 -12L62 -8C68 -6 72 2 72 10Z" fill="${c.rose}"/><path d="M-70 4H72" stroke="${c.cream}" stroke-width="4" opacity=".9"/><path d="M-26 -12C-20 -22 -8 -26 8 -26V-12ZM14 -26C30 -26 40 -20 46 -12H14Z" fill="${c.sky1}" opacity=".95"/><circle cx="-40" cy="12" r="13" fill="${c.dark}"/><circle cx="-40" cy="12" r="6" fill="${c.cream}"/><circle cx="42" cy="12" r="13" fill="${c.dark}"/><circle cx="42" cy="12" r="6" fill="${c.cream}"/><circle cx="66" cy="-2" r="3.5" fill="${night ? '#ffe4a8' : c.cream}"/></g>`;
  s += `</g>`;
  s += `<rect x="-440" y="-440" width="880" height="880" fill="#000" filter="url(#@gl)" opacity=".0"/>`;
  return s;
}

/* ---------- weather lens, nominal radius 100, centred ---------- */
function lensArt(state, sx, sc) {
  const T = {
    sun: { a: '#86b7bb', b: '#f2e4bd', far: '#9dbab6', near: '#52807f', fog: '#fff' },
    rain: { a: '#5b727a', b: '#9fb3b3', far: '#7f9896', near: '#3f6264', fog: '#c8d6d4' },
    snow: { a: '#bfcdd0', b: '#eef0ea', far: '#c6d4d4', near: '#f4f4ee', fog: '#fff' },
    night: { a: '#0d2230', b: '#37595c', far: '#244650', near: '#10272d', fog: '#0d2230' }
  }[state];
  const towers = [[-92, 22, 50], [-68, 16, 80], [-50, 26, 40], [-22, 18, 95], [-2, 24, 60], [24, 16, 100], [42, 28, 55], [68, 20, 75]];
  let s = `<defs><clipPath id="@lc${sx}"><circle r="100"/></clipPath>${lg('ls' + sx, 0, 0, 0, 1, [[0, T.a], [1, T.b]])}</defs>`;
  s += `<g clip-path="url(#@lc${sx})"><rect x="-100" y="-100" width="200" height="200" fill="url(#@ls${sx})"/>`;
  if (state === 'sun') s += `<circle cx="42" cy="-38" r="52" fill="url(#@sunGl)"/><circle cx="42" cy="-38" r="15" fill="#fff6dc"/><ellipse cx="-44" cy="-50" rx="30" ry="6" fill="#fff" opacity=".55" filter="url(#@b3)"/>`;
  if (state === 'rain') s += `<ellipse cx="-30" cy="-62" rx="52" ry="18" fill="#56696f" filter="url(#@b3)"/><ellipse cx="34" cy="-70" rx="50" ry="16" fill="#62767b" filter="url(#@b3)"/><ellipse cx="0" cy="-48" rx="70" ry="14" fill="#6d8187" filter="url(#@b3)" opacity=".8"/>`;
  if (state === 'night') {
    s += `<circle cx="-34" cy="-46" r="15" fill="#f3e8c8"/><circle cx="-28" cy="-50" r="13" fill="#17303b"/><circle cx="-34" cy="-46" r="40" fill="url(#@sunGl)" opacity=".25"/>`;
    [[30, -62], [58, -30], [-8, -70], [64, -66], [10, -34], [-62, -12]].forEach(([x, y], i) => s += `<path d="M${x} ${y - 4}L${x + 1} ${y - 1}L${x + 4} ${y}L${x + 1} ${y + 1}L${x} ${y + 4}L${x - 1} ${y + 1}L${x - 4} ${y}L${x - 1} ${y - 1}Z" fill="#f3e8c8" opacity="${.65 + (i % 3) * .1}"/>`);
  }
  towers.forEach(([x, w, h], i) => {
    s += `<rect x="${x}" y="${46 - h}" width="${w}" height="${h + 60}" fill="${T.far}"/>`;
    if (h > 90) s += `<path d="M${x + w / 2 - 2} ${46 - h}L${x + w / 2} ${46 - h - 16}L${x + w / 2 + 2} ${46 - h}Z" fill="${T.far}"/>`;
    if (state === 'night') for (let yy = 46 - h + 8; yy < 40; yy += 11) if (hash(x, yy) > .5) s += `<rect x="${x + 4}" y="${yy}" width="3" height="4" fill="#f6cc74" opacity=".9"/>`;
  });
  s += `<path d="M-100 52C-50 40 -20 56 30 46S80 44 100 50V100H-100Z" fill="${T.near}"/>`;
  if (state === 'snow') s += `<path d="M-100 62C-50 52 -10 66 40 58S80 56 100 62V100H-100Z" fill="#fff" opacity=".9"/>`;
  if (state === 'sun') s += `<path d="M-60 -100L-26 -100L40 100L6 100Z" fill="#fff" opacity=".28" transform="rotate(8)"/>`;
  if (state === 'rain') {
    for (let i = 0; i < 12; i++) { const x = -80 + hash(i, 5) * 160, y = -40 + hash(i, 6) * 100; s += `<path d="M${x} ${y}l-4 14" stroke="#e4f0ee" stroke-width="1.2" opacity=".4"/>`; }
    [[-54, -14, 7], [-12, 18, 6], [32, -26, 7.5], [58, 20, 5.5], [8, -2, 4.5]].forEach(([x, y, r]) => s += `<g transform="translate(${x} ${y})"><ellipse rx="${r * .85}" ry="${r * 1.2}" fill="#fff" opacity=".12"/><ellipse rx="${r * .85}" ry="${r * 1.2}" fill="none" stroke="#fff" stroke-width="1.2" opacity=".55"/><ellipse cx="${-r * .25}" cy="${-r * .4}" rx="${r * .22}" ry="${r * .35}" fill="#fff" opacity=".9"/><path d="M${-r * .6} ${r * .6}Q0 ${r * 1.3} ${r * .6} ${r * .6}" stroke="#12303a" stroke-width="1.2" fill="none" opacity=".5"/></g>`);
  }
  if (state === 'snow') for (let i = 0; i < 16; i++) { const x = -86 + hash(i, 3) * 172, y = -86 + hash(i, 4) * 140, r = 1.5 + hash(i, 8) * 2.2; s += `<circle cx="${x}" cy="${y}" r="${r}" fill="#fff" opacity=".92"/>`; }
  /* glass */
  s += `<circle r="100" fill="url(#@edge)"/><ellipse cy="82" rx="58" ry="14" fill="#fff" opacity=".22" filter="url(#@b3)"/>`;
  s += `<ellipse cx="-40" cy="-54" rx="46" ry="22" transform="rotate(-32 -40 -54)" fill="url(#@hl)" opacity=".8"/>`;
  s += `<path d="M-84 -34A92 92 0 0 1 -30 -88" fill="none" stroke="#fff" stroke-width="2.4" stroke-linecap="round" opacity=".6"/>`;
  s += `<path d="M60 82A94 94 0 0 0 88 52" fill="none" stroke="#fff" stroke-width="2" stroke-linecap="round" opacity=".28"/></g>`;
  return s;
}
function lensGroup(sc, state, R, sx = '') {
  const k = R / 100;
  return `<g><circle r="${R + 24}" fill="#000" filter="url(#@sh)" opacity=".6"/>` +
    metal(ringS(R + 2, R + 24), 'bD', { patina: 1 }) +
    `<path d="M${-(R + 12) * .72} ${-(R + 12) * .72}A${R + 12} ${R + 12} 0 0 1 ${(R + 12) * .72} ${-(R + 12) * .72}" fill="none" stroke="#fff" stroke-width="3" stroke-linecap="round" opacity=".55" filter="url(#@b1)"/>` +
    `<circle r="${R + 2}" fill="none" stroke="#000" stroke-width="3" opacity=".5"/>` +
    `<g transform="scale(${k})">${lensArt(state, sx, sc)}</g></g>`;
}

/* ---------- clock (centre of dial) ---------- */
function clock(sc, o = {}) {
  let s = `<circle r="162" fill="${sc.frost}"/><circle r="162" fill="url(#@edgeS)" opacity=".35"/>`;
  s += metal(ringS(158, 166), 'bD', { smooth: 1 }) + `<circle r="158" fill="none" stroke="#000" stroke-width="2" opacity=".3"/>`;
  for (let i = 0; i < 60; i++) { const a = i * 6 * Math.PI / 180, l = i % 5 === 0 ? 14 : 6, r2 = 150, w = i % 5 === 0 ? 2.6 : 1.2; s += `<line x1="${Math.sin(a) * (r2 - l)}" y1="${-Math.cos(a) * (r2 - l)}" x2="${Math.sin(a) * r2}" y2="${-Math.cos(a) * r2}" stroke="${sc.hands}" stroke-width="${w}" stroke-linecap="round" opacity=".85"/>`; }
  [['XII', 0, -112], ['III', 112, 8], ['VI', 0, 128], ['IX', -112, 8]].forEach(([t, x, y]) => s += `<text x="${x}" y="${y}" text-anchor="middle" font-family="CSC" font-weight="600" font-size="27" letter-spacing="1" fill="${sc.hands}" opacity=".92">${t}</text>`);
  const hand = (ang, len, w) => `<g transform="rotate(${ang})"><path d="M${-w} 10L${-w * .45} ${-len}L${w * .45} ${-len}L${w} 10Z" fill="${sc.hands}"/></g>`;
  s += `<g filter="url(#@sh2)">${hand(304.5, 84, 6)}${hand(54, 128, 4.2)}</g>`;
  s += `<g transform="rotate(200)"><line y1="26" y2="-136" stroke="${sc.accent === '#8a3b47' && sc.night ? '#d98c96' : sc.accent}" stroke-width="2.2"/><circle cy="-120" r="0"/></g>`;
  s += `<circle r="10" fill="url(#@bD)"/><circle r="3.5" fill="${sc.hands}"/>`;
  return s;
}

/* ---------- dial group, nominal outer radius 470 ---------- */
function dial(sc, o) {
  const ringR = 336, discR = 48, spin = o.spin || 0, style = o.icons || 'A';
  const lbl = sc.label;
  let g = '';
  g += `<circle r="470" fill="#000" filter="url(#@sh)" opacity=".65"/>`;
  g += metal(ringS(455, 471), 'cD');
  g += metal(ringS(428, 456), 'bD', { patina: 1 });
  g += `<path d="M-322 -322A455 455 0 0 1 322 -322" transform="rotate(-4)" fill="none" stroke="#fff" stroke-width="3.5" stroke-linecap="round" opacity=".55" filter="url(#@b1)"/>`;
  g += `<path d="M330 330A455 455 0 0 1 -330 330" fill="none" stroke="#000" stroke-width="5" stroke-linecap="round" opacity=".25" filter="url(#@b1)"/>`;
  g += `<circle r="427" fill="none" stroke="#000" stroke-width="3" opacity=".6"/>`;
  g += `<clipPath id="@dc"><circle r="426"/></clipPath><clipPath id="@rc"><path d="${ringD(226, 426)}" clip-rule="evenodd"/></clipPath>`;
  g += `<g clip-path="url(#@dc)"><use href="#@scene"/>`;
  g += `<g clip-path="url(#@rc)"><g filter="url(#@b8)"><use href="#@scene"/></g><circle r="430" fill="${sc.smoke}" opacity="${sc.smokeA}"/>`;
  g += `<path d="${ringD(226, 426)}" fill-rule="evenodd" fill="url(#@wash)" opacity=".5"/></g>`;
  g += `<circle r="426" fill="url(#@edge)"/>`;
  g += `<path d="M-300 -280A410 410 0 0 1 120 -400" fill="none" stroke="#fff" stroke-width="22" stroke-linecap="round" opacity=".09" filter="url(#@b8)"/></g>`;
  g += metal(ringS(219, 233), 'bD', { patina: 1 });
  g += `<circle r="219" fill="none" stroke="#000" stroke-width="3" opacity=".35"/>`;
  g += `<g transform="scale(.93)">${clock(sc)}</g>`;
  /* finger stop on the bezel at about 4 o'clock */
  g += `<g transform="rotate(128)"><g filter="url(#@sh2)">${metal(rr(-13, -500, 26, 78, 13), 'bH')}<circle cy="-488" r="5" fill="#2a1d0a" opacity=".5"/></g></g>`;
  /* icons */
  const n = 12;
  const pos = i => { const a = (i * 30 + spin) * Math.PI / 180; return [Math.sin(a) * ringR, -Math.cos(a) * ringR]; };
  if (o.mode === 'fling') {
    for (let i = 0; i < n; i++) for (const d of [-26, -17, -9]) { const a = (i * 30 + spin + d) * Math.PI / 180; g += `<circle cx="${Math.sin(a) * ringR}" cy="${-Math.cos(a) * ringR}" r="${discR}" fill="${sc.brass[1]}" opacity="${.22 + (d + 26) / 120}" filter="url(#@b3)"/>`; }
  }
  for (let i = 0; i < n; i++) {
    const [x, y] = pos(i), nm = LABELS[i];
    const press = o.mode === 'tap' && i === (o.tapIdx ?? 2);
    g += `<g transform="translate(${x} ${y})">`;
    if (press) g += `<circle r="${discR + 18}" fill="none" stroke="${sc.glyph}" stroke-width="3" opacity=".9" filter="url(#@gw)"/>`;
    g += icon(sc, nm, discR, style, { press });
    g += `<text y="${discR + 28}" text-anchor="middle" font-family="CSC" font-weight="600" font-size="25" letter-spacing="1.2" fill="${lbl}" stroke="${sc.smoke}" stroke-width="6" stroke-opacity=".75" stroke-linejoin="round" paint-order="stroke">${nm}</text></g>`;
  }
  if (o.mode === 'drag' || o.mode === 'fling') {
    const a0 = (o.arrowFrom ?? -5) * Math.PI / 180, a1 = (o.arrowTo ?? 55) * Math.PI / 180, R = 395;
    g += `<path d="M${Math.sin(a0) * R} ${-Math.cos(a0) * R}A${R} ${R} 0 0 1 ${Math.sin(a1) * R} ${-Math.cos(a1) * R}" fill="none" stroke="#ff9a82" stroke-width="6" stroke-dasharray="4 14" stroke-linecap="round"/>`;
    const ax = Math.sin(a1) * R, ay = -Math.cos(a1) * R, rot = (o.arrowTo ?? 55);
    g += `<path d="M-12 8L0 -14L12 8Z" transform="translate(${ax} ${ay}) rotate(${rot + 90 - 90 + 0})" fill="#ff9a82"/>`;
  }
  if (o.mode === 'drag') { const [fx, fy] = pos(o.fingerIdx ?? 0); g += `<circle cx="${fx}" cy="${fy}" r="58" fill="#fff" opacity=".25" stroke="#fff" stroke-width="3"/>`; }
  if (o.mode === 'tap') { const [fx, fy] = pos(o.tapIdx ?? 2); g += `<circle cx="${fx}" cy="${fy}" r="62" fill="#fff" opacity=".22" stroke="#fff" stroke-width="3"/>`; }
  return g;
}

/* ---------- the whole Home ---------- */
function home(o) {
  const sc = SCH[o.scheme], L = LAY[o.layout || 'i'], P = 'u' + (UID++) + '_';
  const night = sc.night;
  const cx = 540, cy = L.dialCY, ds = L.dialR / 470;
  const lensCx = 70 + L.lensR + 24, lensCy = L.stripY + L.stripH / 2;
  let s = `<svg xmlns="http://www.w3.org/2000/svg" width="1080" height="2400" viewBox="0 0 1080 2400"><defs>${defs(sc)}`;
  s += `<g id="@scene">${sceneArt(sc, night)}</g>`;
  s += `<clipPath id="@pc"><path d="${rrD(44, 140, 992, 2150, 52)}"/></clipPath></defs>`;
  s += `<rect width="1080" height="2400" fill="#000"/>`;
  /* body: brushed chrome (or ivory enamel) */
  s += metal(rr(0, 0, 1080, 2400, 66), 'cH', { k: o.scheme === 'd' ? .18 : .5 });
  if (o.scheme === 'd') s += `<path d="${rrD(0, 0, 1080, 2400, 66)}" fill="#000" filter="url(#@mot)" opacity=".05"/>`;
  /* brass rim + inlay */
  s += `<path d="${rrD(10, 10, 1060, 2380, 58)}${rrD(34, 34, 1012, 2332, 44)}" fill-rule="evenodd" fill="url(#@bV)"/>`;
  s += `<path d="${rrD(10, 10, 1060, 2380, 58)}${rrD(34, 34, 1012, 2332, 44)}" fill-rule="evenodd" fill="#000" filter="url(#@pat)" opacity=".2"/>`;
  s += `<path d="${rrD(10, 10, 1060, 2380, 58)}${rrD(34, 34, 1012, 2332, 44)}" fill-rule="evenodd" fill="#000" filter="url(#@gd)" opacity=".4"/>`;
  s += `<rect x="12" y="12" width="1056" height="2376" rx="57" fill="none" stroke="#fff" stroke-width="2" opacity=".55"/>`;
  s += `<rect x="36" y="36" width="1008" height="2328" rx="43" fill="none" stroke="${sc.ink}" stroke-width="3.5" opacity=".85"/>`;
  s += `<rect x="40.5" y="40.5" width="999" height="2319" rx="39" fill="none" stroke="#fff" stroke-width="1.2" opacity=".3"/>`;
  /* painted panel */
  s += `<g filter="url(#@sh2)"><path d="${rrD(44, 140, 992, 2150, 52)}" fill="url(#@paint)"/></g>`;
  s += `<g clip-path="url(#@pc)">`;
  s += `<path d="${rrD(44, 140, 992, 2150, 52)}" fill="#000" filter="url(#@motd)" opacity=".13"/><path d="${rrD(44, 140, 992, 2150, 52)}" fill="#000" filter="url(#@mot)" opacity=".07"/><path d="${rrD(44, 140, 992, 2150, 52)}" fill="#000" filter="url(#@gl)" opacity=".18"/><path d="${rrD(44, 140, 992, 2150, 52)}" fill="#000" filter="url(#@gd)" opacity=".18"/>`;
  for (let i = 0; i < 48; i++) { const a = i * 7.5 * Math.PI / 180; s += `<line x1="${cx}" y1="${cy}" x2="${cx + Math.sin(a) * 1600}" y2="${cy - Math.cos(a) * 1600}" stroke="${sc.sc.cream}" stroke-width="${i % 2 ? 1.5 : 3}" opacity="${night ? .05 : .07}"/>`; }
  for (const r of [498, 520, 542]) s += `<circle cx="${cx}" cy="${cy}" r="${r * ds}" fill="none" stroke="${sc.sc.cream}" stroke-width="1.5" opacity=".13"/>`;
  s += `<rect x="44" y="140" width="992" height="2150" fill="url(#@wash)"/>`;
  s += `<path d="${rrD(44, 140, 992, 2150, 52)}" fill="none" stroke="#000" stroke-width="26" opacity=".35" filter="url(#@b8)"/></g>`;
  s += `<path d="${rrD(47, 143, 986, 2144, 49)}" fill="none" stroke="url(#@bD)" stroke-width="5"/><path d="${rrD(52, 148, 976, 2134, 44)}" fill="none" stroke="${sc.ink}" stroke-width="2" opacity=".7"/>`;
  /* night lamp glow */
  if (sc.glow) {
    s += `<ellipse cx="540" cy="${L.placY + L.placH / 2}" rx="600" ry="${L.placH * .9}" fill="url(#@glow)" opacity=".85"/>`;
    s += `<ellipse cx="620" cy="${lensCy}" rx="460" ry="170" fill="url(#@glow)" opacity=".28"/>`;
    s += `<ellipse cx="540" cy="${L.trayY + L.trayH / 2}" rx="520" ry="${L.trayH * .6}" fill="url(#@glow)" opacity=".28"/>`;
  }
  /* head: date, battery, punch-hole mark */
  const plate = (x, w) => `<g filter="url(#@sh2)">${metal(rr(x, 36, w, 64, 20), 'bV', { patina: 1 })}</g>${rr(x + 6, 42, w - 12, 52, 15)('fill="url(#@plate)"')}${rr(x + 6, 42, w - 12, 52, 15)('fill="none" stroke="#000" stroke-width="3" opacity=".5"')}<path d="M${x + 20} 46H${x + w - 20}" stroke="#fff" stroke-width="1.5" opacity=".18"/>`;
  s += plate(58, 286);
  s += `<text x="201" y="80" text-anchor="middle" font-family="CSC" font-weight="600" font-size="32" letter-spacing="2.5" fill="${sc.plateFg}">TUE 28 OCT</text>`;
  s += plate(736, 286);
  s += `<text x="772" y="80" font-family="CG" font-weight="600" font-size="34" fill="${sc.plateFg}">78%</text>`;
  s += rr(850, 56, 140, 26, 13)('fill="#02080a" opacity=".85"') + rr(853, 59, 108, 20, 10)(`fill="url(#@bV)"`) + rr(853, 59, 108, 8, 4)('fill="#fff" opacity=".3"') + rr(850, 56, 140, 26, 13)('fill="none" stroke="#fff" stroke-width="1.4" opacity=".35"');
  s += `<circle cx="540" cy="50" r="25" fill="#000"/><circle cx="540" cy="50" r="25" fill="none" stroke="#fff" stroke-width="1.5" opacity=".18"/>`;
  /* weather strip */
  const sx = 150, sw = 854;
  s += `<g filter="url(#@sh)">${metal(rr(sx, L.stripY, sw, L.stripH, L.stripH / 2), 'cV')}</g>`;
  s += rr(sx + 2, L.stripY + 2, sw - 4, L.stripH - 4, L.stripH / 2 - 2)('fill="none" stroke="#fff" stroke-width="2.5" opacity=".7"');
  s += rr(sx, L.stripY, sw, L.stripH, L.stripH / 2)(`fill="none" stroke="${sc.ink}" stroke-width="3" opacity=".7"`);
  const px0 = lensCx + L.lensR + 32, px1 = 976, py = L.stripY + L.stripH * .17, ph = L.stripH * .66;
  s += rr(px0 - 6, py - 6, px1 - px0 + 12, ph + 12, ph / 2 + 4)('fill="url(#@cVr)"');
  s += rr(px0, py, px1 - px0, ph, ph / 2)('fill="url(#@plate)"') + rr(px0, py, px1 - px0, ph, ph / 2)('fill="none" stroke="#000" stroke-width="4" opacity=".6"');
  s += `<path d="M${px0 + ph / 2} ${py + 5}H${px1 - ph / 2}" stroke="#fff" stroke-width="1.6" opacity=".16"/>`;
  const wst = o.weather || 'rain', wtxt = { sun: ['24°', 'Sunny'], rain: ['17°', 'Light rain'], snow: ['-2°', 'Light snow'], night: ['9°', 'Clear night'] }[wst];
  const fs = L.stripH * .5;
  s += `<text x="${px0 + 34}" y="${py + ph / 2 + fs * .3}" font-family="CG" font-weight="600" font-size="${fs}" fill="${sc.plateFg}">${wtxt[0]}</text>`;
  s += `<text x="${px1 - 34}" y="${py + ph / 2 + 12}" text-anchor="end" font-family="CSC" font-weight="600" font-size="${L.stripH * .25}" letter-spacing="2" fill="${sc.plateFg}">${wtxt[1]}</text>`;
  s += `<g transform="translate(${lensCx} ${lensCy})">${lensGroup(sc, wst, L.lensR)}</g>`;
  /* dial */
  s += `<g transform="translate(${cx} ${cy}) scale(${ds})">${dial(sc, o)}</g>`;
  /* placard */
  const pY = L.placY, pH = L.placH;
  s += `<g filter="url(#@sh)">${metal(rr(90, pY, 900, pH, 24), 'bV', { patina: 1 })}</g>`;
  s += rr(90, pY, 900, pH, 24)('fill="none" stroke="#fff" stroke-width="2" opacity=".5"');
  s += rr(106, pY + 16, 868, pH - 32, 12)('fill="url(#@paper)"') + rr(106, pY + 16, 868, pH - 32, 12)('fill="#000" filter="url(#@gl)" opacity=".12"') + rr(106, pY + 16, 868, pH - 32, 12)('fill="#000" filter="url(#@mot)" opacity=".10"');
  s += rr(106, pY + 16, 868, pH - 32, 12)(`fill="none" stroke="#000" stroke-width="5" opacity=".35"`);
  s += `<path d="M106 ${pY + 16}H974V${pY + pH * .45}Z" fill="#fff" opacity=".12"/>`;
  [[104, pY + 14], [976, pY + 14], [104, pY + pH - 14], [976, pY + pH - 14]].forEach(([x, y]) => s += `<g transform="translate(${x} ${y})"><circle r="7.5" fill="url(#@bD)"/><path d="M-4 0H4" stroke="#2a1d0a" stroke-width="1.6" opacity=".6"/><circle r="7.5" fill="none" stroke="#000" stroke-width="1" opacity=".4"/></g>`);
  const lf = Math.min(40, (pH - 50) / 3 * .7), mid = pY + pH / 2;
  s += `<text x="240" y="${mid + 14}" text-anchor="middle" font-family="CSC" font-weight="600" font-size="${pH > 250 ? 50 : 42}" letter-spacing="5" fill="${sc.accent}">TODAY</text>`;
  s += `<path d="M398 ${pY + 38}V${pY + pH - 38}" stroke="${sc.ink}" stroke-width="1.6" opacity=".4"/>`;
  [['10:30', 'Example meeting'], ['13:00', 'Example errand'], ['17:45', 'Example reminder']].forEach(([t, w], i) => {
    const y = mid + (i - 1) * ((pH - 50) / 3) + lf * .32;
    s += `<text x="436" y="${y}" font-family="CG" font-weight="700" font-size="${lf}" fill="${sc.ink}">${t}</text><text x="${436 + lf * 2.5}" y="${y}" font-family="CG" font-weight="600" font-size="${lf}" fill="${sc.ink}">${w}</text>`;
  });
  /* pinned tray */
  const tY = L.trayY, tH = L.trayH;
  s += `<g filter="url(#@sh)">${metal(rr(76, tY, 928, tH, 52), 'cV')}</g>`;
  s += rr(76, tY, 928, tH, 52)(`fill="none" stroke="${sc.ink}" stroke-width="3" opacity=".7"`);
  s += rr(98, tY + 22, 884, tH - 44, 34)('fill="url(#@cVr)"') + rr(106, tY + 30, 868, tH - 60, 28)('fill="url(#@tray)"');
  s += rr(106, tY + 30, 868, tH - 60, 28)('fill="none" stroke="#000" stroke-width="6" opacity=".5"');
  s += `<g filter="url(#@sh2)">${metal(rr(440, tY - 4, 200, 42, 12), 'bV', { patina: 1 })}</g><text x="540" y="${tY + 26}" text-anchor="middle" font-family="CSC" font-weight="600" font-size="24" letter-spacing="4" fill="#2a1d0a">PINNED</text>`;
  const dy = tY + tH * .47;
  PINNED.forEach((nm, i) => {
    const x = 240 + i * 300;
    s += `<g transform="translate(${x} ${dy})">${icon(sc, nm, L.discR, o.icons || 'A')}<text y="${L.discR + 42}" text-anchor="middle" font-family="CSC" font-weight="600" font-size="30" letter-spacing="1.5" fill="${sc.trayLabel}">${nm}</text></g>`;
  });
  /* glass sheen across the whole face (very light) */
  s += `<path d="M44 140H560L44 700Z" fill="#fff" opacity="${night ? .035 : .06}" clip-path="url(#@pc)"/>`;
  if (o.over) s += o.over;
  s += `</svg>`;
  return s.replace(/@/g, P);
}
function mount(html) { document.body.insertAdjacentHTML('beforeend', html); }
