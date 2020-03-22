
import UIKit


class UserDataViewController: UIViewController, OnboardingChild {
    var state: OnboardingState = .none
    var parentOnboardingViewController: OnboardingViewController?
    
    @IBOutlet weak var usernameTextField: UITextField!
    weak var delegate: OnboardingDelegate?

    @IBOutlet weak var emailTextField: UITextField!
    
    @IBOutlet weak var userImageView: UIImageView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        view.addGestureRecognizer(UITapGestureRecognizer(target: self, action: #selector(viewTapped)))
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
    
    @IBAction func selectImageButtonTapped(_ sender: Any) {
        
    }
    
}
