# help commands start
alias vim=nvim
alias v=vim
alias s='source ~/.zshrc'
alias ..='cd ../'
alias ...='cd ../../'
alias ~='cd ~'
alias f="find . -name $1"
alias ip="ifconfig | grep 'inet' | grep -Fv 127.0.0.1 | awk '{print $2}'"
alias sleep="sudo systemctl hybrid-sleep"
alias hib="sudo systemctl hibernate"
alias theme="java -jar ~/dotfiles/clj_scripts/theme/target/uberjar/theme-0.1.0-SNAPSHOT-standalone.jar $1"
alias wtheme="theme 'w'"
alias btheme="theme 'b'"
alias c="clear"
alias fz='bash ~/dotfiles/fuz.sh'

# python on mac
alias python=/usr/local/bin/python3.8
alias pip=/usr/local/bin/pip3.8

# android commands start
alias apkinstall="adb devices | tail -n +2 | cut -sf 1 | xargs -I X adb -s X install -r $1"
alias ai="apkinstall"
alias rmapp="adb devices | tail -n +2 | cut -sf 1 | xargs -I X adb -s X uninstall $1"
alias clearappcache='adb shell pm clear $1'
alias adb_root_remount_shell="adb root; adb remount; adb shell"
# android commands end

#translation start
alias р='trans -b ru:en'
alias e='trans en:ru -brief'
alias eng='trans en:ru'
#translation end

# settings files start
alias zshrc='vim ~/dotfiles/.zshrc'
alias vimrc='vim ~/dotfiles/.vimrc'
alias inputrc='vim ~/dotfiles/.inputrc'
alias idearc='vim ~/dotfiles/.ideavimrc'
alias zathurarc='vim ~/.config/zathura/zathurarc'
# settings files end

# gradle start
alias stop='./gradlew --stop'
alias gc='./gradlew clean'
# gradle end

ZSH_THEME="robbyrussell"
plugins=(
    git
    adb
    gradle
    vi-mode
    python
    postgres
    docker
    docker-compose
)
source $ZSH/oh-my-zsh.sh

source ~/work/.aliases.sh
source ~/.ssh-aliases.sh

export NVM_DIR="$HOME/.nvm"
[ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"  # This loads nvm
[ -s "$NVM_DIR/bash_completion" ] && \. "$NVM_DIR/bash_completion"  # This loads nvm bash_completion
