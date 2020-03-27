
import UIKit

class KeyboardHandler: NSObject {
//    typealias KeyboardCompletionClosure = (KeyboardDisplayType) -> Void
    
    var constraint: NSLayoutConstraint
    var view: UIView
    
    
    enum KeyboardDisplayType {
        case show
        case hide
    }
    
    var keyboardsHandlings: [KeyboardHandling] = []
    
    init(constraint: NSLayoutConstraint, view: UIView) {
        self.constraint = constraint
        self.view = view
    }
    
    func registerNotifications() {
        NotificationCenter.default.addObserver(self, selector: #selector(animateKeyboard), name: UIResponder.keyboardWillShowNotification, object: nil)
        NotificationCenter.default.addObserver(self, selector: #selector(animateKeyboard), name: UIResponder.keyboardWillHideNotification, object: nil)
    }
    
    @objc func animateKeyboard(_ notification: Notification) {
        let userInfo = notification.userInfo!
        let keyboardHeight = (userInfo[UIResponder.keyboardFrameEndUserInfoKey] as! NSValue).cgRectValue.height
        let duration = userInfo[UIResponder.keyboardAnimationDurationUserInfoKey] as! Double
        let curve = userInfo[UIResponder.keyboardAnimationCurveUserInfoKey] as! UInt
        let moveUp = (notification.name == UIResponder.keyboardWillShowNotification)
        
        constraint.constant = moveUp ? keyboardHeight : 0
        print("constraint constant: \(constraint.constant) screenheight \(UIScreen.main.bounds.height)")
        let options = UIView.AnimationOptions(arrayLiteral: UIView.AnimationOptions.Element(rawValue: curve))
        
        
        UIView.animate(withDuration: duration, delay: 0, options: options, animations: {
            self.view.layoutIfNeeded()
            self.keyboardHandlingExecuteAction()
        }, completion: { finished in
            self.keyboardHandlingStateChanged(moveUp ? .show : .hide)
        })
    }
    
    func keyboardHandlingExecuteAction() {
        keyboardsHandlings.forEach { $0.keyboardActionTriggered() }
    }
    
    func keyboardHandlingStateChanged(_ state: KeyboardDisplayType) {
        keyboardsHandlings.forEach { $0.keyboardStateChanged(state) }
    }
    
    deinit {
        NotificationCenter.default.removeObserver(self)
    }
}
