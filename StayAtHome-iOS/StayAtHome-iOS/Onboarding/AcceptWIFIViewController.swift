
import UIKit

class AcceptWIFIViewController: UIViewController, OnboardingChild {
    var state: OnboardingState = .none
    weak var delegate: OnboardingDelegate?
    var parentOnboardingViewController: OnboardingViewController?
    
    @IBOutlet weak var acceptWifiSwitch: UISwitch!
    @IBOutlet weak var wifiNameLabel: UILabel!
    
    @IBOutlet weak var headerLabel: UILabel!
    @IBOutlet weak var switchDescriptionLabel: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        NotificationCenter.default.addObserver(forName: UIApplication.willEnterForegroundNotification, object: nil, queue: nil) { [weak self] (notificaiton) in
            self?.receiveWifiName()
        }
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        receiveWifiName()
    }
    
    @IBAction func acceptWifiSwitchValueChanged(_ sender: Any) {
        if acceptWifiSwitch.isOn {
            state = .done
        } else {
            state = .none
        }
        
        delegate?.stateChanged(self, to: state)
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
                strongSelf.acceptWifiSwitch.isEnabled = false
                strongSelf.switchDescriptionLabel.isEnabled = false
                strongSelf.wifiNameLabel.text = "--"
                return
            }
            
            strongSelf.switchDescriptionLabel.isEnabled = true
            strongSelf.acceptWifiSwitch.isEnabled = true
            strongSelf.headerLabel.text = "Ist das dein Wifi Zuhause?"
            strongSelf.wifiNameLabel.text = ssid
        })
    }
    

}
