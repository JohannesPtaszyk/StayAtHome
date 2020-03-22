
import UIKit


class UserDataViewController: UIViewController, OnboardingChild {
    var state: OnboardingState = .none
    var parentOnboardingViewController: OnboardingViewController?
    
    @IBOutlet weak var usernameTextField: UITextField!
    weak var delegate: OnboardingDelegate?
    @IBOutlet weak var userNameBackgroundView: UIView!
    @IBOutlet weak var emailTextField: UITextField!
    @IBOutlet weak var emailBackgroundView: UIView!
    @IBOutlet weak var userImageView: UIImageView!
    
    lazy var imagePicker = ImagePicker(presentationController: self, delegate: self)
    
    override func viewDidLoad() {
        super.viewDidLoad()
        userNameBackgroundView.layer.cornerRadius = 22
        emailBackgroundView.layer.cornerRadius = 22
        
        userNameBackgroundView.layer.borderColor = UIColor.black.cgColor
        emailBackgroundView.layer.borderColor = UIColor.black.cgColor
        
        emailBackgroundView.layer.borderWidth = 2
        userNameBackgroundView.layer.borderWidth = 2
        
        usernameTextField.attributedPlaceholder = NSAttributedString(string: usernameTextField.placeholder ?? "",
                                                                     attributes: [NSAttributedString.Key.foregroundColor: UIColor.black.withAlphaComponent(0.7)])
        
        emailTextField.attributedPlaceholder = NSAttributedString(string: emailTextField.placeholder ?? "",
                                                                     attributes: [NSAttributedString.Key.foregroundColor: UIColor.black.withAlphaComponent(0.7)])
        
        view.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(viewTapped)))
        
        userImageView.isUserInteractionEnabled = true
        userImageView.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(selectImageTapped)))
        userImageView.layer.cornerRadius = userImageView.bounds.height / 2
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
        if emailTextField.text != "" && usernameTextField.text != "" {
            state = .done
            delegate?.stateChanged(self, to: state)
        }
    }
    
    @objc func selectImageTapped() {
        imagePicker.present(from: view)
    }
}

extension UserDataViewController: ImagePickerDelegate {
    func didSelect(image: UIImage?) {
        userImageView.image = image
    }
    
}
