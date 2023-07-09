
import UIKit

class MovieViewController: UIViewController, UITableViewDelegate, UITableViewDataSource
{
    @IBOutlet weak var tableView: UITableView!
        
    var movies: [Movie] = []
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        
        fetchMovies { [weak self] movies, error in
            DispatchQueue.main.async
            {
                if let movies = movies
                {
                    if movies.isEmpty
                    {
                        // Display a message for no data
                        self?.displayErrorMessage("No movies available.")
                    } else {
                        self?.movies = movies
                        self?.tableView.reloadData()
                    }
                } else if let error = error {
                    if let urlError = error as? URLError, urlError.code == .timedOut
                    {
                        // Handle timeout error
                        self?.displayErrorMessage("Request timed out.")
                    } else {
                        // Handle other errors
                        self?.displayErrorMessage(error.localizedDescription)
                    }
                }
            }
        }
    }
    
    func displayErrorMessage(_ message: String)
    {
        let alertController = UIAlertController(title: "Error", message: message, preferredStyle: .alert)
        alertController.addAction(UIAlertAction(title: "OK", style: .default, handler: nil))
        present(alertController, animated: true, completion: nil)
    }
    
    func fetchMovies(completion: @escaping ([Movie]?, Error?) -> Void)
    {
        guard let url = URL(string: "https://mdev1001-m2023-api.onrender.com/api/list") else
        {
            completion(nil, nil) // Handle URL error
            return
        }

        URLSession.shared.dataTask(with: url) { data, _, error in
            if let error = error {
                completion(nil, error) // Handle network error
                return
            }

            guard let data = data else {
                completion(nil, nil) // Handle empty response
                return
            }

            do {
                let movies = try JSONDecoder().decode([Movie].self, from: data)
                completion(movies, nil) // Success
            } catch {
                completion(nil, error) // Handle JSON decoding error
            }
        }.resume()
    }


    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int
    {
        return movies.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell
    {
        let cell = tableView.dequeueReusableCell(withIdentifier: "MovieCell", for: indexPath) as! MovieTableViewCell
                        
                
        let movie = movies[indexPath.row]
                        
        cell.titleLabel?.text = movie.title
        cell.studioLabel?.text = movie.studio
        cell.ratingLabel?.text = "\(movie.criticsRating)"
                
        // Set the background color of criticsRatingLabel based on the rating
        let rating = movie.criticsRating
                           
        if rating > 7
        {
            cell.ratingLabel.backgroundColor = UIColor.green
            cell.ratingLabel.textColor = UIColor.black
        } else if rating > 5 {
            cell.ratingLabel.backgroundColor = UIColor.yellow
            cell.ratingLabel.textColor = UIColor.black
        } else {
            cell.ratingLabel.backgroundColor = UIColor.red
            cell.ratingLabel.textColor = UIColor.white
        }
        return cell
    }
}
