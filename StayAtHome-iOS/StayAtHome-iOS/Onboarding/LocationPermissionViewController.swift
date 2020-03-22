
import UIKit

class LocationPermissionViewController: UIViewController, OnboardingChild {
    
    var state: OnboardingState = .none
    weak var delegate: OnboardingDelegate?
    var parentOnboardingViewController: OnboardingViewController?
    
    override func viewDidLoad() {
        super.viewDidLoad()

    }
    
    @IBAction func requestPermissionButtonTapped(_ sender: Any) {
        
        parentOnboardingViewController?.gameCoordinator?.managerProvider.locationManager.askForLocationService({ [weak self] (authenticated, location) in
            guard let strongSelf = self else { return }
            
            if authenticated {
                strongSelf.state = .done
                strongSelf.delegate?.stateChanged(strongSelf, to: strongSelf.state)
            } else {
                //TODO error handling
            }
            
        })
        
    }
    

}
