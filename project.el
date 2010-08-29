;; M-x eval-buffer
;; M-x clj-adder
;; M-x swank-clojure-project
;;
;; and to start jetty, in clojure
;; (load-file "script/run.clj")

(defun indent-or-expand (arg)
  "Either indent according to mode, or expand the word preceding
point."
  (interactive "*P")
  (if (and
       (or (bobp) (= ?w (char-syntax (char-before))))
       (or (eobp) (not (= ?w (char-syntax (char-after))))))
      (dabbrev-expand arg)
    (indent-according-to-mode)))
 
(defun my-tab-fix ()
  (local-set-key [tab] 'indent-or-expand))
 
(defmacro defwrk (name description &rest files)
    `(defun ,name ()
 	,description
 	(interactive)
 	(let ((files '(,@files)))
  	  (loop while files collecting (find-file (pop files))))))

(defun clj-snippets nil
  "Start project with some custom setting I have in my global .emacs"
  (interactive)
  (setq project-directory "~/projects")
  (setq project-name "snippet")

  (setq default-directory (concat project-directory "/" project-name "/"))

  (load (concat project-directory "/gists/421306/clojure-font-lock-setup.el"))
  (add-hook 'slime-connected-hook 'slime-redirect-inferior-output)
  (setq slime-protocol-version 'ignore)

  ;; add hooks for modes you want to use the tab completion for:
  ;;(add-hook 'c-mode-hook          'my-tab-fix)
  (add-hook 'sh-mode-hook         'my-tab-fix)
  (add-hook 'emacs-lisp-mode-hook 'my-tab-fix)
  (add-hook 'clojure-mode-hook    'my-tab-fix)
  (add-hook 'slime-connected-hook 'slime-redirect-inferior-output)

  (autoload 'paredit-mode "paredit"
    "Minor mode for pseudo-structurally editing Lisp code." 
    t)
  (add-hook 'clojure-mode-hook (lambda () (paredit-mode +1)))

  (defwrk clj-current "important files in this project"
     "~/projects/snippets/project.clj"
     "~/projects/snippets/project.el"
     "~/projects/snippets/src/snippets/core.clj"
     "~/projects/snippets/user.clj")
  (clj-current)
  )
