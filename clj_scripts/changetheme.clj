#! usr/bin/sh
#_(
   #_DEPS is same format as deps.edn. Multiline is okay.
   DEPS='
   {:deps
   	{cli-matic {:mvn/version "0.3.11"}}}
   '

   #_You can put other options here
   OPTS='
   -J-Dclojure.spec.skip-macros=true
   -J-Xms256m -J-Xmx256m
   -J-client
   -J-Dclojure.spec.skip-macros=true
   '
   exec clojure $OPTS -Sdeps "$DEPS" "$0" "$@"
)

;;#-*- mode: clojure -*-
;; setting common dependencies

(require '[clojure.string :as str])

;; robot help functions

(import java.awt.Robot)
(import java.awt.event.KeyEvent)

(def robot (Robot.))

(defn robot-hot-keys [keys]
  (doseq [key keys] (.keyPress robot key))
  (.delay robot 60)
  (doseq [key keys] (.keyRelease robot key)))

(defn robot-print [^String s]
  (for [byte (.getBytes s)
        :let [code (int byte)
              code (if (< 96 code 123) (- code 32) code)]]
    (doto robot
      (.delay 5)
      (.keyPress code)
      (.keyRelease code))))

(defn robot-type [^Integer i]
  (doto robot
    (.delay 40)
    (.keyPress i)
    (.keyRelease i)))

;; file help functions

(defn expand-home [s]
  (if (.startsWith s "~")
    (clojure.string/replace-first s "~" (System/getProperty "user.home"))
    s))

(defn file-by-lines [f-name]
  (str/split (slurp f-name) #"\n"))

(defn write-lines [f-name lines]
  (->> (str/join "\n" lines) (spit f-name)))

;; zathura

(def zathura (expand-home "~/dotfiles/.config/zathura/zathurarc"))

(defn zathura-change-colors-with [f path]
  (let [lines           (file-by-lines path)
       [s title colors] (partition-by #(.contains % "Colours") lines)
       colors'          (map f colors)]
    (->> (concat s title colors')
         (write-lines path))))

(defn zathura-comment-colors []
  (zathura-change-colors-with #(if (= "set" (re-find #"\w+" %))
                                 (str "#" %)
                                 %)
                              zathura))

(defn zathura-uncomment-colors []
  (zathura-change-colors-with #(if (= "#set" (re-find #"#\w+" %))
                                 (subs % 1)
                                 %)
                              zathura))

;; spacemacs

(def spacemacs (expand-home "~/dotfiles/.spacemacs"))
(def spacemacs-light "doom-one-light")
(def spacemacs-dark  "doom-tomorrow-night")

;; TODO: check if spacemacs is opened,
;; than press alt+m  -> load-theme -> doom-one-light -> enter -> y

(defn spacemacs-set-theme [theme]
  ;; (do
  ;;   (.delay robot 400)
  ;;   (robot-hot-keys [KeyEvent/VK_ALT KeyEvent/VK_X])
  ;;   (.delay robot 100)
  ;;   (robot-print (str "load-theme\n" theme "\n")))
  (->> (file-by-lines spacemacs)
       (map (fn [line]
              (if (.contains line "dotspacemacs-themes ")
                (str "   dotspacemacs-themes '(" theme)
                line)))
       (write-lines spacemacs)))

;; vim

(def vim (expand-home "~/dotfiles/.vimrc"))

(defn vim-bg [light?] (if light? "light" "dark"))
(defn vim-theme [light?] (if light? "one" "xoria256"))

(defn vim-set-theme [light?]
  (->> (file-by-lines vim)
       (map (fn [line]
              (cond (.contains line "colorscheme")
                    (str "colorscheme " (vim-theme light?)),
                    (.contains line "set background")
                    (str "set background=" (vim-bg light?)),
                    :else line)))
       (write-lines vim)))

;; desktop theme

(use '[clojure.java.shell :only [sh]])

(defn desktop-set-theme [& {:keys [property theme]}]
  {:pre [(#{"ThemeName" "IconThemeName"} property)]}
  (clojure.java.shell/sh
   "sh"
   "-c"
   (format "xfconf-query -c xsettings -p /Net/%s -s %s" property theme)))

;; chrome dark-mode plugin

(defn chrome-toggle-dark-mode-plugin []
  (let [runtime          (Runtime/getRuntime)
        keys-dark-toggle [KeyEvent/VK_SHIFT KeyEvent/VK_ALT KeyEvent/VK_D]
        keys-exit-chrome [KeyEvent/VK_CONTROL KeyEvent/VK_Q]]
    (.exec runtime "google-chrome-stable")
    (.delay robot 700)
    (robot-hot-keys keys-dark-toggle)
    (.delay robot 1000)
    (robot-hot-keys keys-exit-chrome)))

;; apply themes

(def input (delay (first *command-line-args*)))

(cond (= @input "b")
      (do (zathura-uncomment-colors)
          (spacemacs-set-theme spacemacs-dark)
          (desktop-set-theme :property "ThemeName" :theme "Arc-Maia-Dark")
          (desktop-set-theme :property "IconThemeName" :theme "Paper")
          (chrome-toggle-dark-mode-plugin)
          (vim-set-theme false)
          )

      (= @input "w")
      (do (zathura-comment-colors)
          (spacemacs-set-theme spacemacs-light)
          (desktop-set-theme :property "ThemeName" :theme "Mist")
          (desktop-set-theme :property "IconThemeName" :theme "Paper-Mono-Dark")
          (chrome-toggle-dark-mode-plugin)
          (vim-set-theme true)
          )

      :else
      (throw (IllegalArgumentException.
              (str "unknown input " input ", expected: (b)lack or (w)hite"))))

(System/exit 0) ;; due to https://dev.clojure.org/jira/browse/CLJ-959
