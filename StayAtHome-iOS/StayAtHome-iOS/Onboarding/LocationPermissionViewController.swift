
import UIKit

class LocationPermissionViewController: UIViewController, OnboardingChild {
    
    var state: OnboardingState = .none
    weak var delegate: OnboardingDelegate?
    var parentOnboardingViewController: OnboardingViewController?
    
    @IBOutlet weak var locationButton: LightRoundButton!
    override func viewDidLoad() {
        super.viewDidLoad()
        
    }
    
    @IBAction func requestPermissionButtonTapped(_ sender: Any) {
        
        parentOnboardingViewController?.gameCoordinator?.managerProvider.locationManager.askForLocationService({ [weak self] (authenticated, location) in
            guard let strongSelf = self else { return }
            
            if authenticated {
                strongSelf.state = .done
                strongSelf.delegate?.stateChanged(strongSelf, to: strongSelf.state)
                strongSelf.locationButton.isEnabled = false
                strongSelf.locationButton.setTitle("Super, Danke!", for: .disabled)
                strongSelf.locationButton.backgroundColor = strongSelf.locationButton.backgroundColor?.withAlphaComponent(0.5)
                
            } else {
                //TODO error handling
            }
            
        })
        
    }
    

}
