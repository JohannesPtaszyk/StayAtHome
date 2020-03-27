
import UIKit

class AcceptWIFIViewController: UIViewController, OnboardingChild {
    
    var state: OnboardingState = .none
    weak var delegate: OnboardingDelegate?
    var parentOnboardingViewController: OnboardingViewController?
    
    @IBOutlet weak var headerLabel: UILabel!
    @IBOutlet weak var switchDescriptionLabel: UILabel!
    @IBOutlet weak var acceptWifiButton: LightRoundButton!
    var wifiName: String?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        acceptWifiButton.isEnabled = false
        
        NotificationCenter.default.addObserver(forName: UIApplication.willEnterForegroundNotification, object: nil, queue: nil) { [weak self] (notificaiton) in
            self?.receiveWifiName()
        }
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        receiveWifiName()
    }
    
    func receiveWifiName() {
        parentOnboardingViewController?.gameCoordinator?.receiveSSID({ [weak self] (ssid, error) in
            guard let strongSelf = self else { return }
            
            if let error = error {
                
                switch error {
                case .general:
                    strongSelf.headerLabel.text = "Etwas ist schiefgelaufen beim Abfragen deines Wifi's."
                    break
                case .noLocationService:
                    strongSelf.headerLabel.text = "Du musst deinen Standort aktivieren, damit wir auf deine Wifi-Einstellungen zugreifen können!"
                    break
                case .noWifi:
                    strongSelf.headerLabel.text = "Du bist mit keinem Wifi Netzwerk verbunden!"
                    
                    break
                }
                strongSelf.switchDescriptionLabel.isEnabled = false
                strongSelf.acceptWifiButton.isEnabled = false
                return
            }
            strongSelf.wifiName = ssid
            strongSelf.acceptWifiButton.isEnabled = true
        })
    }
    
    
    @IBAction func acceptWifiButtonTapped(_ sender: Any) {
        state = .done
        delegate?.stateChanged(self, to: state)
        acceptWifiButton.isEnabled = false
        acceptWifiButton.setTitle("Super, danke!", for: .disabled)
        acceptWifiButton.backgroundColor = acceptWifiButton.backgroundColor?.withAlphaComponent(0.5)
    }
    
}
