
import UIKit


class UserDataViewController: UIViewController, OnboardingChild {
    
    var state: OnboardingState = .none
    var parentOnboardingViewController: OnboardingViewController?
    
    weak var delegate: OnboardingDelegate?
    
    @IBOutlet weak var userImageView: UIImageView!
    
    @IBOutlet weak var scrollView: UIScrollView!
    @IBOutlet weak var userNameRoundTextField: RoundTextField!
    @IBOutlet weak var emailRoundTextField: RoundTextField!
    @IBOutlet weak var scrollViewBottomConstraint: NSLayoutConstraint!
    
    @IBOutlet weak var emailBottomConstraint: NSLayoutConstraint!
    lazy var imagePicker = ImagePicker(presentationController: self, delegate: self)
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        view.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(viewTapped)))
        
        userNameRoundTextField.textField.delegate = self
        emailRoundTextField.textField.delegate = self
        
        userImageView.isUserInteractionEnabled = true
        userImageView.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(selectImageTapped)))
        userImageView.layer.cornerRadius = userImageView.bounds.height / 2
    }
    
    deinit {
        NotificationCenter.default.removeObserver(self)
    }
    
    @objc func viewTapped() {
        view.endEditing(true)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        checkIfIsDone()
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        checkIfIsDone()
    }
    
    func checkIfIsDone() {
        if emailRoundTextField.text != "" && userNameRoundTextField.text != "" {
            state = .done
            delegate?.stateChanged(self, to: state)
        }
    }
    
    @objc func selectImageTapped() {
        imagePicker.present(from: view)
    }
    
    func scrollToEmailTextField() {
        self.scrollView.scrollRectToVisible(self.emailRoundTextField.frame, animated: false)
    }
    
}

extension UserDataViewController: ImagePickerDelegate {
    func didSelect(image: UIImage?) {
        userImageView.image = image
    }
    
}

extension UserDataViewController: KeyboardHandling {

    func keyboardStateChanged(_ state: KeyboardHandler.KeyboardDisplayType) {
        
    }
    
    func keyboardActionTriggered() {
        scrollToEmailTextField()
    }
    
}

extension UserDataViewController: UITextFieldDelegate {
    
    func textFieldDidEndEditing(_ textField: UITextField) {
        checkIfIsDone()
    }
}
