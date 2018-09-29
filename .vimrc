set backspace=indent,eol,start
set relativenumber
set number
set autoindent
set clipboard+=unnamed  " use the clipboards of vim and win

" manage plugins
filetype plugin indent on

:let g:notes_directories = ['~/Yandex.Disk.localized/notes/vim']

"{{{ spelling
"set spellfile=~/.vim/spell/{language}.{encoding}.add
nnoremap <silent> <leader>se :setlocal spell! spelllang=en<cr>
nnoremap <silent> <leader>sr :setlocal spell! spelllang=ru<cr>
"}}}

"{{{ russian mapping
set keymap=russian-jcukenmac
set iminsert=0
set imsearch=0
highlight lCursor guifg=NONE guibg=Cyan

inoremap <c-l> <c-^>
"}}}

"{{{ syntax
:syntax on
set background=dark
colorscheme torte

:command FormatJson %!python -c "import json, sys, collections; print json.dumps(json.load(sys.stdin, object_pairs_hook=collections.OrderedDict), ensure_ascii=False, indent=4)"

au BufRead,BufNewFile *.lib set filetype=sh
"}}}

set nocompatible

"{{{ search
set ignorecase
set incsearch
set hlsearch
:hi Search guibg=Yellow guifg=Black ctermbg=Yellow ctermfg=Black
"}}}

set smartindent
set shiftwidth=4 smarttab expandtab

set path+=**
set tags=./tags,tags;$HOME

"{{{escaping to the normal mode with Tab.
" See there why this takes place: http://vim.wikia.com/wiki/Avoid_the_escape_key
vnoremap <tab> <esc>
inoremap <tab> <esc>
inoremap <S-tab> <space><space><space><space>
nmap <tab> :noh<Enter>:echom ""<Enter>
nnoremap r<tab> <nop>
"}}}

"{{{ positioning
nmap <space> <nop>
map J 13jzz
map K 13kzz
map H 15h
map L 15l
map <space>h ^
map <space>l $
"alt + l
noremap ¬ 7l
"alt + h
noremap ˙ 7h
"alt + j
noremap ∆ 4j
"alt + k
noremap ˚ 4k
"}}}

"{{{ reg 
"alt + q
noremap œ @

"nnoremap <expr> m '"'.nr2char(getchar()).'y'
"vnoremap <expr> m '"'.nr2char(getchar()).'y'
"nnoremap <expr> <space>r '"'.nr2char(getchar()).'p'
"vnoremap <expr> <space>r '"'.nr2char(getchar()).'p'

"delete all line without putting it in registers
noremap D V"_d
"}}}

"{{{ making habitual hotkeys work in vim
"alt + d  -> copy and paste the line
nnoremap ∂ Yp

"alt + a  -> select all
nnoremap å ggVG

"alt + s  -> save
nnoremap ß :w<Enter>

"alt + x  -> save and quit
nnoremap ≈ :x<Enter>

"alt + q  -> quit
nnoremap œ :q<Enter>

"alt + Q  -> force quit
nnoremap Œ :q!<Enter>
"}}}

"{{{ editing
nnoremap <space>o o<Esc>
"}}}

"{{{ search, replace, regex 
nnoremap / /\v
vnoremap / /\v
"}}}
