
import UIKit


class MainViewController: UIViewController, GameDelegate {

    @IBOutlet weak var userImageView: UIImageView!
    
    @IBOutlet weak var userNameLabel: UILabel!
    @IBOutlet weak var scoreLabel: UILabel!
    @IBOutlet weak var levelNameLabel: UILabel!
    @IBOutlet weak var scoreDescription: UILabel!
    
    var gameCoordinator: GameCoordinator?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        userImageView.layer.cornerRadius = userImageView.bounds.height / 2
        
        userNameLabel.text = gameCoordinator?.user?.username
        userImageView.image = gameCoordinator?.user?.image
        
        guard let user = gameCoordinator?.user else {
            return
        }
        
        gameCoordinator?.game.startGame(for: user)
        gameCoordinator?.game.delegate = self
        
        levelNameLabel.text = gameCoordinator?.user?.level.title
        userNameLabel.text = gameCoordinator?.user?.username
        scoreLabel.text = String(user.score)

        scoreDescription.text = gameCoordinator?.game.generateRandomCheer()
    }
    
    override var preferredStatusBarStyle: UIStatusBarStyle {
        return .darkContent
    }
    
    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        
        navigationController?.navigationBar.isHidden = true
    }
    
    func game(_ game: Game, stateChanged state: GameState) {
        scoreLabel.text = String(game.score)
        
        scoreDescription.text = game.generateRandomCheer()
        gameCoordinator?.saveUser()
        
    }
    
    @IBAction func settingsButtonTapped(_ sender: Any) {
        let settingsViewController = UIStoryboard.main.instantiateViewController(withIdentifier: Constants.Storyboards.ViewController.settings)
        settingsViewController.modalPresentationStyle = .fullScreen
        present(settingsViewController, animated: true, completion: nil)
    }
    
    @IBAction func friendsButtonTapped(_ sender: Any) {
        let friendsViewController = UIStoryboard.main.instantiateViewController(withIdentifier: Constants.Storyboards.ViewController.friends)
        
        friendsViewController.modalPresentationStyle = .fullScreen
        present(friendsViewController, animated: true, completion: nil)
    }
    
    @IBAction func challengeButtonTapped(_ sender: Any) {
        let challangesViewController = UIStoryboard.main.instantiateViewController(withIdentifier: Constants.Storyboards.ViewController.challenges)
        challangesViewController.modalPresentationStyle = .fullScreen
        present(challangesViewController, animated: true, completion: nil)
    }
    
    @IBAction func leaderBoardButtonTapped(_ sender: Any) {
        let leaderBoardViewController = UIStoryboard.main.instantiateViewController(withIdentifier: Constants.Storyboards.ViewController.leaderBoard)
        leaderBoardViewController.modalPresentationStyle = .fullScreen
        present(leaderBoardViewController, animated: true, completion: nil)
    }
}
